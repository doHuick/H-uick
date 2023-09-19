package com.dohit.huick.api.user.dto;

import org.springframework.stereotype.Component;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Component
public class UserApiDto {

	@Getter
	@ToString
	@Builder
	public static class Request {

		private Long userId;
		private String name;
		private String rrn;
		private String address;
	}

}
