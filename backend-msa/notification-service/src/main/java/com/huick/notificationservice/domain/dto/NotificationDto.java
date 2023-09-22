package com.huick.notificationservice.domain.dto;

import java.time.LocalDateTime;

import com.huick.notificationservice.domain.constant.NotificationType;
import com.huick.notificationservice.domain.entity.Notification;

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

	@Builder
	private NotificationDto(Long notificationId, Long userId, Long contractId, NotificationType notificationType, LocalDateTime createdTime) {
		this.notificationId = notificationId;
		this.userId = userId;
		this.contractId = contractId;
		this.notificationType = notificationType;
		this.createdTime = createdTime;
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
}
