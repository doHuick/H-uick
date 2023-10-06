package com.dohit.huick.domain.notification.dto;

import java.time.LocalDateTime;

import com.dohit.huick.api.notification.dto.NotificationApiDto;
import com.dohit.huick.domain.notification.constant.NotificationType;
import com.dohit.huick.domain.notification.entity.Notification;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationDto {
	Long notificationId;
	Long userId;
	Long contractId;
	NotificationType notificationType;
	LocalDateTime createdTime;

	String title;
	String body;

	@Builder
	private NotificationDto(Long notificationId, Long userId, Long contractId,
		NotificationType notificationType, LocalDateTime createdTime, String title, String body) {
		this.notificationId = notificationId;
		this.userId = userId;
		this.contractId = contractId;
		this.notificationType = notificationType;
		this.createdTime = createdTime;
		this.title = title;
		this.body = body;
	}

	static public NotificationDto of(Long userId, Long contractId, NotificationType notificationType) {
		return NotificationDto.builder()
			.userId(userId)
			.contractId(contractId)
			.notificationType(notificationType)
			.build();
	}

	public static NotificationDto from(Notification notification) {
		return NotificationDto.builder()
			.notificationId(notification.getNotificationId())
			.userId(notification.getUserId())
			.contractId(notification.getContractId())
			.notificationType(notification.getNotificationType())
			.createdTime(notification.getCreatedTime())
			.build();
	}

	public static NotificationDto from(NotificationApiDto.Request request) {
		return NotificationDto.builder()
			.userId(request.getUserId())
			.title(request.getTitle())
			.body(request.getBody())
			.build();
	}

}
