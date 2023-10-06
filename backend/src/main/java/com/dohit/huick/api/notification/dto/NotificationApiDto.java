package com.dohit.huick.api.notification.dto;

import java.time.LocalDateTime;

import com.dohit.huick.domain.notification.constant.NotificationType;
import com.dohit.huick.domain.notification.dto.NotificationDto;
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
		String token;

		Long userId;
		String title;
		String body;

		@Builder
		private Request(String token, Long userId, String title, String body) {
			this.token = token;
			this.userId = userId;
			this.title = title;
			this.body = body;
		}
	}

	@Getter
	@NoArgsConstructor
	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class Response {
		Long notificationId;
		Long userId;
		Long contractId;
		NotificationType notificationType;
		LocalDateTime createdTime;

		@Builder
		private Response(Long notificationId, Long userId, Long contractId, NotificationType notificationType,
			LocalDateTime createdTime) {
			this.notificationId = notificationId;
			this.userId = userId;
			this.contractId = contractId;
			this.notificationType = notificationType;
			this.createdTime = createdTime;
		}

		public static Response from(NotificationDto notificationDto) {
			return Response.builder()
				.notificationId(notificationDto.getNotificationId())
				.userId(notificationDto.getUserId())
				.contractId(notificationDto.getContractId())
				.notificationType(notificationDto.getNotificationType())
				.createdTime(notificationDto.getCreatedTime())
				.build();
		}
	}
}
