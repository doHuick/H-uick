package com.dohit.huick.domain.notification.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.dohit.huick.domain.notification.constant.NotificationType;
import com.dohit.huick.domain.notification.dto.NotificationDto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long notificationId;

	@Column(nullable = false)
	Long userId;

	@Column(nullable = false)
	Long contractId;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	NotificationType notificationType;

	@CreatedDate
	LocalDateTime createdTime;

	@Builder
	private Notification(Long notificationId, Long userId, Long contractId, NotificationType notificationType,
		LocalDateTime createdTime) {
		this.notificationId = notificationId;
		this.userId = userId;
		this.contractId = contractId;
		this.notificationType = notificationType;
		this.createdTime = createdTime;
	}

	public static Notification from(NotificationDto notificationDto) {
		return Notification.builder()
			.userId(notificationDto.getUserId())
			.contractId(notificationDto.getContractId())
			.notificationType(notificationDto.getNotificationType())
			.build();
	}
}
