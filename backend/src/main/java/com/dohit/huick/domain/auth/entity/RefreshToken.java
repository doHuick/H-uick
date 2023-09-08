package com.dohit.huick.domain.auth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "RefreshToken")
public class RefreshToken {

	@Id
	@Column(name = "refresh_token_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long refreshTokenId;

	@Column(name = "user_id", unique = true)
	@NotNull
	private Long userId;

	@Column(name = "refresh_token", length = 256)
	@NotNull
	@Size(max = 256)
	private String refreshToken;

	public RefreshToken(
		@NotNull @Size(max = 64) Long userId,
		@NotNull @Size(max = 256) String refreshToken
	) {
		this.userId = userId;
		this.refreshToken = refreshToken;
	}

}
