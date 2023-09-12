package com.dohit.huick.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dohit.huick.global.token.AuthTokenProvider;

@Configuration
public class JwtConfig {

	@Value("${jwt.secret}")
	private String secret;

	@Bean
	public AuthTokenProvider jwtProvider() {
		return AuthTokenProvider.from(secret);
	}
}
