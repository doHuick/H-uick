package com.dohit.huick.domain.auth.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dohit.huick.domain.auth.constant.Role;
import com.dohit.huick.domain.auth.entity.RefreshToken;
import com.dohit.huick.domain.auth.repository.RefreshTokenRepository;
import com.dohit.huick.global.error.ErrorCode;
import com.dohit.huick.global.error.exception.AuthenticationException;
import com.dohit.huick.global.error.exception.BusinessException;
import com.dohit.huick.global.property.AppProperties;
import com.dohit.huick.global.token.AuthToken;
import com.dohit.huick.global.token.AuthTokenProvider;
import com.dohit.huick.global.util.CookieUtil;
import com.dohit.huick.global.util.HeaderUtil;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuthService {

	private final RefreshTokenRepository refreshTokenRepository;
	private final AuthTokenProvider authTokenProvider;
	private final AppProperties appProperties;

	private final static String REFRESH_TOKEN = "refresh_token";
	private final static long THREE_DAYS_MSEC = 259200000;

	public void logout(String accessToken) throws BusinessException {
		AuthToken authToken = authTokenProvider.convertAuthToken(accessToken);
		String name = authTokenProvider.getAuthentication(authToken).getName();
		if (name == null) {
			throw new BusinessException(ErrorCode.NOT_EXIST_USER);
		}
		AuthToken refreshToken = authTokenProvider.createAuthToken(name, new Date(System.currentTimeMillis()));
		refreshTokenRepository.save(RefreshToken.of(Long.valueOf(name), refreshToken.getToken()));
	}

	public String refreshToken(HttpServletRequest request, HttpServletResponse response, String refreshToken) throws
		BusinessException {

		// access token을 헤더에서 가져오기
		String accessToken = HeaderUtil.getAccessToken(request);

		// access token 확인
		AuthToken authToken = authTokenProvider.convertAuthToken(accessToken);
		if (!authToken.getTokenClaimsRegardlessOfExpire()) {
			// access token이 올바르지 않으면 예외 발생
			throw new AuthenticationException(ErrorCode.NOT_VALID_TOKEN);
		}

		// expired access token 인지 확인
		Claims claims = authToken.getExpiredTokenClaims();
		if (claims == null) {
			// access token이 만료되지 않았다면 null을 반환
			return null;
		}

		String userId = claims.getSubject();
		Role role = Role.of(claims.get("role", String.class));

		AuthToken authRefreshToken = authTokenProvider.convertAuthToken(refreshToken);
		
		if (!authRefreshToken.getTokenClaimsRegardlessOfExpire()) {
			throw new AuthenticationException(ErrorCode.NOT_VALID_TOKEN);
		}

		// userId refresh token 으로 DB 확인
		RefreshToken refreshTokenInRedis = refreshTokenRepository.findByUserId(Long.valueOf(userId))
			.orElseThrow(() -> new AuthenticationException(
				ErrorCode.NOT_VALID_TOKEN));

		if (!refreshTokenInRedis.getRefreshToken().equals(refreshToken)) {
			throw new AuthenticationException(ErrorCode.NOT_VALID_TOKEN);
		}

		Date now = new Date();
		AuthToken newAccessToken = authTokenProvider.createAuthToken(
			userId,
			role.getCode(),
			new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
		);
		long validTime = authRefreshToken.getTokenClaims().getExpiration().getTime() - now.getTime();

		// refresh 토큰 기간이 3일 이하로 남은 경우, refresh 토큰 갱신
		if (validTime <= THREE_DAYS_MSEC) {
			// refresh 토큰 설정
			long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();

			authRefreshToken = authTokenProvider.createAuthToken(
				appProperties.getAuth().getTokenSecret(),
				new Date(now.getTime() + refreshTokenExpiry)
			);

			// DB에 refresh 토큰 업데이트
			refreshTokenInRedis.setRefreshToken(authRefreshToken.getToken());
			refreshTokenRepository.save(refreshTokenInRedis);

			int cookieMaxAge = (int)refreshTokenExpiry / 60;
			CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
			CookieUtil.addCookie(response, REFRESH_TOKEN, authRefreshToken.getToken(), cookieMaxAge);
		}

		return newAccessToken.getToken();
	}
}
