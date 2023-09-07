package com.dohit.huick.global.token;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.dohit.huick.global.error.ErrorCode;
import com.dohit.huick.global.error.exception.AuthenticationException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthTokenProvider {

	private final Key key;
	private static final String AUTHORITIES_KEY = "role";

	@Builder
	private AuthTokenProvider(Key key) {
		this.key = key;
	}

	public static AuthTokenProvider from(String secret) {
		return AuthTokenProvider.builder()
			.key(Keys.hmacShaKeyFor(secret.getBytes()))
			.build();
	}

	public AuthToken createAuthToken(String id, Date expiry) {
		return AuthToken.of(id, expiry, key);
	}

	public AuthToken createAuthToken(String id, String role, Date expiry) {
		return AuthToken.of(id, role, expiry, key);
	}

	public AuthToken convertAuthToken(String token) {
		return AuthToken.of(token, key);
	}

	public Authentication getAuthentication(AuthToken authToken) {

		if (authToken.validate()) {

			Claims claims = authToken.getTokenClaims();
			Collection<? extends GrantedAuthority> authorities =
				Arrays.stream(new String[] {claims.get(AUTHORITIES_KEY).toString()})
					.map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList());

			log.debug("claims subject := [{}]", claims.getSubject());
			User principal = new User(claims.getSubject(), "", authorities);

			return new UsernamePasswordAuthenticationToken(principal, authToken, authorities);
		} else {
			throw new AuthenticationException(ErrorCode.NOT_VALID_TOKEN);
		}
	}
}
