package com.dohit.huick.api.notification.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dohit.huick.api.notification.dto.NotificationApiDto;
import com.dohit.huick.domain.notification.dto.DeviceTokenDto;
import com.dohit.huick.domain.notification.service.NotificationService;
import com.dohit.huick.global.userinfo.UserInfo;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {
	private final NotificationService notificationService;

	@PostMapping("/token")
	public ResponseEntity<Void> createDeviceToken(@UserInfo Long userId, @RequestBody NotificationApiDto.Request request) {
		notificationService.createDeviceToken(DeviceTokenDto.of(userId, request.getToken()));
		return ResponseEntity.ok().build();
	}
}
