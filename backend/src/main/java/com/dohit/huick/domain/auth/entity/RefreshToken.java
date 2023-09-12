package com.dohit.huick.domain.auth.entity;

import org.springframework.data.annotation.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RefreshToken {

	@Id
	private Long userId;
	private String refreshToken;

	@Builder
	public RefreshToken(Long userId, String refreshToken) {
		this.userId = userId;
		this.refreshToken = refreshToken;
	}

	static public RefreshToken of(Long userId, String refreshToken) {
		return RefreshToken.builder()
			.userId(userId)
			.refreshToken(refreshToken)
			.build();
	}
}
