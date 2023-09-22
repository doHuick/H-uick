package com.huick.notificationservice.api.controller.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.Path;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// import com.dohit.huick.global.userinfo.UserInfo;
import com.huick.notificationservice.api.controller.dto.NotificationApiDto;
import com.huick.notificationservice.domain.dto.DeviceTokenDto;
import com.huick.notificationservice.domain.service.NotificationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class NotificationController {
	private final NotificationService notificationService;

	@PostMapping("/token/{userId}")
	public ResponseEntity<Void> createDeviceToken(@PathVariable Long userId, @RequestBody NotificationApiDto.Request request) {
		notificationService.createDeviceToken(DeviceTokenDto.of(userId, request.getToken()));
		return ResponseEntity.ok().build();
	}

	@GetMapping("/{userId}")
	public ResponseEntity<List<NotificationApiDto.Response>> getNotificationByUserId(@PathVariable Long userId) {
		return ResponseEntity.ok().body(notificationService.getNotificationUserId(userId).stream().map(
			NotificationApiDto.Response::from).collect(
			Collectors.toList()));
	}
}
