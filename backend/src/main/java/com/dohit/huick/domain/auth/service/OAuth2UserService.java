package com.dohit.huick.domain.auth.service;

import java.time.LocalDateTime;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.dohit.huick.domain.auth.constant.Role;
import com.dohit.huick.domain.auth.constant.SocialType;
import com.dohit.huick.domain.auth.entity.UserPrincipal;
import com.dohit.huick.domain.banking.service.BankingService;
import com.dohit.huick.domain.user.entity.OAuth2UserInfo;
import com.dohit.huick.domain.user.entity.OAuth2UserInfoFactory;
import com.dohit.huick.domain.user.entity.User;
import com.dohit.huick.domain.user.repository.UserRepository;
import com.dohit.huick.global.error.ErrorCode;
import com.dohit.huick.global.error.exception.AuthenticationException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

	private final UserRepository userRepository;
	private final BankingService bankingService;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User user = super.loadUser(userRequest);

		try {
			return this.createOrUpdate(userRequest, user);
		} catch (AuthenticationException ex) {
			throw ex;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
		}
	}

	private OAuth2User createOrUpdate(OAuth2UserRequest userRequest, OAuth2User user) {
		SocialType socialType = SocialType.valueOf(
			userRequest.getClientRegistration().getRegistrationId().toUpperCase());

		OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(socialType, user.getAttributes());
		User savedUser = userRepository.findBySocialId(String.valueOf(userInfo.getId())).orElse(null);

		if (savedUser != null) {
			if (socialType != savedUser.getSocialType()) {
				throw new AuthenticationException(ErrorCode.INVALID_SOCIAL_TYPE);
			}
			updateUser(savedUser, userInfo);
		} else {
			savedUser = createUser(userInfo, socialType);
		}

		return UserPrincipal.of(savedUser, user.getAttributes());
	}

	private User createUser(OAuth2UserInfo userInfo, SocialType socialType) {
		LocalDateTime now = LocalDateTime.now();
		User user = User.builder()
			.socialId(String.valueOf(userInfo.getId()))
			.socialType(socialType)
			.role(Role.USER)
			.build(); // 다른 정보들을 더 넣는 수정 작업 필요

		user = userRepository.saveAndFlush(user);
		createAccount(user.getUserId());

		return user;
	}

	private User updateUser(User user, OAuth2UserInfo userInfo) {
		return user;
	}

	private void createAccount(Long userId) {
		bankingService.createAccount(userId);
		bankingService.transferRandomMoney(bankingService.getAccountByUserId(userId).getAccountNumber());
	}

}
