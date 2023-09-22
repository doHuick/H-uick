package com.huick.bankingservice.domain.dto;

import java.time.LocalDateTime;

import com.huick.bankingservice.domain.constant.NotificationType;

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
}
