package com.dohit.huick.api.user.dto;

import lombok.Getter;

public class UserApiDto {

	@Getter
	public static class Request {

		private Long userId;
		private String name;
		private String rrn;
		private String address;
	}

}
