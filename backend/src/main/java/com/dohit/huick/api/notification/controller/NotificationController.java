package com.dohit.huick.api.notification.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dohit.huick.api.notification.dto.NotificationApiDto;
import com.dohit.huick.domain.notification.dto.NotificationDto;
import com.dohit.huick.domain.notification.service.NotificationService;
import com.google.firebase.messaging.FirebaseMessagingException;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {
	private final NotificationService notificationService;

	@PostMapping
	public ResponseEntity<Void> sendMessage(@RequestBody NotificationApiDto.Request request) throws
		FirebaseMessagingException {
		notificationService.sendNotificationByToken(NotificationDto.from(request) );
		return ResponseEntity.ok().build();
	}
}
