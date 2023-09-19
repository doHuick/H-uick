package com.dohit.huick.domain.user.dto;

import com.dohit.huick.api.user.dto.UserApiDto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDto {

	private final Long userId;
	private final String name;
	private final String rrn;
	private final String address;

	@Builder
	private UserDto(Long userId, String name, String rrn, String address) {
		this.userId = userId;
		this.name = name;
		this.rrn = rrn;
		this.address = address;
	}

	public static UserDto of(Long userId, UserApiDto.Request request) {
		return UserDto.builder()
			.userId(userId)
			.name(request.getName())
			.rrn(request.getRrn())
			.address(request.getAddress())
			.build();
	}
}
