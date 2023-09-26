package com.huick.notificationservice.api.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.huick.notificationservice.domain.constant.NotificationType;
import com.huick.notificationservice.domain.dto.NotificationDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class NotificationApiDto {

	@Getter
	@NoArgsConstructor
	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class Request {
		String token;

		@Builder
		private Request(String token) {
			this.token = token;
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
