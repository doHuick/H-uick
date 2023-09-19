package com.dohit.huick.domain.notification.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.dohit.huick.domain.notification.dto.DeviceTokenDto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeviceToken {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long deviceTokenId;

	@Column(nullable = false)
	Long userId;

	@Column(nullable = false)
	String deviceToken;

	@Builder
	private DeviceToken(Long deviceTokenId, Long userId, String deviceToken) {
		this.deviceTokenId = deviceTokenId;
		this.userId = userId;
		this.deviceToken = deviceToken;
	}

	public static DeviceToken from(DeviceTokenDto deviceTokenDto) {
		return DeviceToken.builder()
			.userId(deviceTokenDto.getUserId())
			.deviceToken(deviceTokenDto.getDeviceToken())
			.build();
	}
}
