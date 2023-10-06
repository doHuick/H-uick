package com.dohit.huick.global.token;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class AuthToken {

	private final String token;
	private final Key key;

	private static final String AUTHORITIES_KEY = "role";

	@Builder
	private AuthToken(String token, Key key) {
		this.token = token;
		this.key = key;
	}

	static AuthToken of(String token, Key key) {
		return AuthToken.builder()
			.token(token)
			.key(key)
			.build();
	}

	static AuthToken of(String id, Date expiry, Key key) {
		return AuthToken.builder()
			.token(Jwts.builder()
				.setSubject(id)
				.signWith(key, SignatureAlgorithm.HS256)
				.setExpiration(expiry)
				.compact())
			.key(key)
			.build();
	}

	static AuthToken of(String id, String role, Date expiry, Key key) {
		return AuthToken.builder()
			.token(Jwts.builder()
				.setSubject(id)
				.claim(AUTHORITIES_KEY, role)
				.signWith(key, SignatureAlgorithm.HS256)
				.setExpiration(expiry)
				.compact())
			.key(key)
			.build();
	}

	public boolean validate() {
		return this.getTokenClaims() != null;
	}

	public Claims getTokenClaims() {
		try {
			return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();
		} catch (SecurityException e) {
			log.info("Invalid JWT signature.");
		} catch (MalformedJwtException e) {
			log.info("Invalid JWT token.");
		} catch (ExpiredJwtException e) {
			log.info("Expired JWT token.");
		} catch (UnsupportedJwtException e) {
			log.info("Unsupported JWT token.");
		} catch (IllegalArgumentException e) {
			log.info("JWT token compact of handler are invalid.");
		}
		return null;
	}

	public boolean getTokenClaimsRegardlessOfExpire() {
		Claims claims = null;
		boolean flag = false;
		try {
			claims = Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();
			flag = true;
		} catch (SecurityException e) {
			log.info("Invalid JWT signature.");
		} catch (MalformedJwtException e) {
			log.info("Invalid JWT token.");
		} catch (ExpiredJwtException e) {
			log.info("Expired JWT token.");
			flag = true;
		} catch (UnsupportedJwtException e) {
			log.info("Unsupported JWT token.");
		} catch (IllegalArgumentException e) {
			log.info("JWT token compact of handler are invalid.");
		}
		return flag;
	}

	public Claims getExpiredTokenClaims() {
		try {
			Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();
		} catch (ExpiredJwtException e) {
			log.info("Expired JWT token.");
			return e.getClaims();
		}
		return null;
	}
}
