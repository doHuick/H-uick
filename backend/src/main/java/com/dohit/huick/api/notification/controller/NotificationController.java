package com.dohit.huick.api.notification.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dohit.huick.domain.notification.service.NotificationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {
	private final NotificationService notificationService;

}
