package com.huick.userservice.domain.service;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huick.userservice.domain.dto.UserDto;
import com.huick.userservice.domain.entity.User;
import com.huick.userservice.domain.repository.UserRepository;
import com.huick.userservice.global.error.ErrorCode;
import com.huick.userservice.global.error.exception.AuthenticationException;
import com.huick.userservice.global.error.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	// private final RefreshTokenRepository refreshTokenRepository;
	// private final AuthTokenProvider authTokenProvider;
	// private final BankingService bankingService;

	@Transactional
	public void signup(UserDto userDto) throws BusinessException {
		User user = userRepository.findByUserId(userDto.getUserId())
			.orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXIST_USER));
		user.signup(userDto);
	}

	// @Transactional
	// public void withdraw() throws BusinessException {
	// 	String name = SecurityContextHolder.getContext().getAuthentication().getName();
	// 	if (name == null) {
	// 		throw new BusinessException(ErrorCode.NOT_EXIST_USER);
	// 	}
	//
	// 	Long userId = Long.valueOf(name);
	// 	User user = userRepository.findByUserId(userId)
	// 		.orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXIST_USER));
	// 	user.withdraw();
	// 	AuthToken refreshToken = authTokenProvider.createAuthToken(name, new Date(System.currentTimeMillis()));
	// 	refreshTokenRepository.save(RefreshToken.of(Long.valueOf(name), refreshToken.getToken()));
	// }

	public UserDto getUserByUserId(Long userId) {

		UserDto userDto = UserDto.from(userRepository.findByUserId(userId).orElseThrow(() -> new AuthenticationException(ErrorCode.NOT_EXIST_USER)));

		// 페인 클라이언트 사용해서 유저아이디로 계정 정보 가져오기
		// bankingService.getAccountByUserId(userId);

		// return UserDto.of(userDto, bankingService.getAccountByUserId(userId));

		return userDto;
	}

}
