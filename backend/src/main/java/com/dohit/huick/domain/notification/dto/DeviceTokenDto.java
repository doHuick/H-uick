package com.dohit.huick.domain.notification.dto;

import com.dohit.huick.domain.notification.entity.DeviceToken;

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

	public static DeviceTokenDto from(DeviceToken deviceToken) {
		return DeviceTokenDto.builder()
			.deviceTokenId(deviceToken.getDeviceTokenId())
			.userId(deviceToken.getUserId())
			.deviceToken(deviceToken.getDeviceToken())
			.build();
	}

	public static DeviceTokenDto of(Long userId, String deviceToken) {
		return DeviceTokenDto.builder()
			.userId(userId)
			.deviceToken(deviceToken)
			.build();
	}
}
