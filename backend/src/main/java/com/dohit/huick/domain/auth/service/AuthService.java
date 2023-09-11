package com.dohit.huick.domain.auth.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dohit.huick.domain.auth.repository.RefreshTokenRepository;
import com.dohit.huick.global.error.ErrorCode;
import com.dohit.huick.global.error.exception.BusinessException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuthService {

	private final RefreshTokenRepository refreshTokenRepository;

	public void logout() throws BusinessException {
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		if (name == null) {
			throw new BusinessException(ErrorCode.NOT_EXIST_USER);
		}

		Long userId = Long.valueOf(name);
		refreshTokenRepository.deleteByUserId(userId);
	}
}
