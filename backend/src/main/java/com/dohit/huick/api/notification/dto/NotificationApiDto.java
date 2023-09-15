package com.dohit.huick.api.notification.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class NotificationApiDto {

	@Getter
	@NoArgsConstructor
	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class Request {
		Long userId;
		String token;
		String title;
		String body;

		@Builder
		private Request(Long userId, String token, String title, String body) {
			this.userId = userId;
			this.token = token;
			this.title = title;
			this.body = body;
		}
	}
}
