package com.dohit.huick.domain.user.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dohit.huick.domain.auth.repository.RefreshTokenRepository;
import com.dohit.huick.domain.user.dto.UserDto;
import com.dohit.huick.domain.user.entity.User;
import com.dohit.huick.domain.user.repository.UserRepository;
import com.dohit.huick.global.error.ErrorCode;
import com.dohit.huick.global.error.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final RefreshTokenRepository refreshTokenRepository;

	@Transactional
	public void signup(UserDto userDto) throws BusinessException {
		User user = userRepository.findByUserId(userDto.getUserId())
			.orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXIST_USER));
		user.signup(userDto);
	}

	@Transactional
	public void withdraw() throws BusinessException {
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		if (name == null) {
			throw new BusinessException(ErrorCode.NOT_EXIST_USER);
		}

		Long userId = Long.valueOf(name);
		User user = userRepository.findByUserId(userId)
			.orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXIST_USER));

		user.withdraw();
		refreshTokenRepository.deleteByUserId(userId);
	}

}
