package com.dohit.huick.domain.auth.service;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dohit.huick.domain.auth.entity.RefreshToken;
import com.dohit.huick.domain.auth.repository.RefreshTokenRepository;
import com.dohit.huick.global.error.ErrorCode;
import com.dohit.huick.global.error.exception.BusinessException;
import com.dohit.huick.global.token.AuthToken;
import com.dohit.huick.global.token.AuthTokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuthService {

	private final RefreshTokenRepository refreshTokenRepository;
	private final AuthTokenProvider authTokenProvider;

	public void logout(String accessToken) throws BusinessException {
		AuthToken authToken = authTokenProvider.convertAuthToken(accessToken);
		String name = authTokenProvider.getAuthentication(authToken).getName();
		if (name == null) {
			throw new BusinessException(ErrorCode.NOT_EXIST_USER);
		}
		AuthToken refreshToken = authTokenProvider.createAuthToken(name, new Date(System.currentTimeMillis()));
		refreshTokenRepository.save(RefreshToken.of(Long.valueOf(name), refreshToken.getToken()));
	}
}
