package com.dohit.huick.api.notification.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dohit.huick.domain.notification.dto.DeviceTokenDto;
import com.dohit.huick.domain.notification.service.NotificationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {
	private final NotificationService notificationService;

	@PostMapping("/token")
	public ResponseEntity<Void> createDeviceToken(Long userId, String deviceToken) {
		notificationService.createDeviceToken(DeviceTokenDto.of(userId, deviceToken));
		return ResponseEntity.ok().build();
	}
}
