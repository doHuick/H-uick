package com.dohit.huick.api.auth.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Getter;

public class AuthApiDto {

	@Getter
	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class Response {
		private String token;

		@Builder
		private Response(String token) {
			this.token = token;
		}

		public static Response from(String token) {
			return Response.builder()
				.token(token)
				.build();
		}
	}
}
