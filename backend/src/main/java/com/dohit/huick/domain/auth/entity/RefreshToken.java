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

	public RefreshToken(
		@NotNull @Size(max = 64) Long userId,
		@NotNull @Size(max = 256) String refreshToken
	) {
		this.userId = userId;
		this.refreshToken = refreshToken;
	}

}
