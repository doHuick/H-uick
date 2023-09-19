package com.dohit.huick.domain.notification.constant;

public enum NotificationType {
	UPCOMING_TRANSFER("이체 예정"),
	TRANSFER_SUCCESS("이체 성공"),
	OVERDUE("연체");


	private final String notificationType;

	NotificationType(String notificationType) {
		this.notificationType = notificationType;
	}
}
