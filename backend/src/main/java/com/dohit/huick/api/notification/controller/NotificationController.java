package com.dohit.huick.api.notification.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dohit.huick.api.notification.dto.NotificationApiDto;
import com.dohit.huick.domain.notification.dto.DeviceTokenDto;
import com.dohit.huick.domain.notification.dto.NotificationDto;
import com.dohit.huick.domain.notification.service.NotificationService;
import com.dohit.huick.global.userinfo.UserInfo;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {
	private final NotificationService notificationService;

	@PostMapping("/token")
	public ResponseEntity<Void> createDeviceToken(@UserInfo Long userId,
		@RequestBody NotificationApiDto.Request request) {
		notificationService.createDeviceToken(DeviceTokenDto.of(userId, request.getToken()));
		return ResponseEntity.ok().build();
	}

	@GetMapping("/me")
	public ResponseEntity<List<NotificationApiDto.Response>> getNotificationByUserId(@UserInfo Long userId) {
		return ResponseEntity.ok().body(notificationService.getNotificationUserId(userId).stream().map(
			NotificationApiDto.Response::from).collect(
			Collectors.toList()));
	}

	@PostMapping
	public ResponseEntity<Void> sendNotification(@RequestBody NotificationApiDto.Request request) {
		notificationService.sendNotificationDirectly(NotificationDto.from(request));
		return ResponseEntity.ok().build();
	}

}
