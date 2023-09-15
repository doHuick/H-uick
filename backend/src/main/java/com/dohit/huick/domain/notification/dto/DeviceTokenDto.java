package com.dohit.huick.domain.notification.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DeviceTokenDto {
	Long deviceTokenId;

	Long userId;

	String deviceToken;

	@Builder
	private DeviceTokenDto(Long deviceTokenId, Long userId, String deviceToken) {
		this.deviceTokenId = deviceTokenId;
		this.userId = userId;
		this.deviceToken = deviceToken;
	}
}
