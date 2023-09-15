package com.dohit.huick.domain.notification.service;

import org.springframework.stereotype.Service;

import com.dohit.huick.domain.notification.dto.NotificationDto;
import com.dohit.huick.domain.user.repository.UserRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NotificationService {
	private final FirebaseMessaging firebaseMessaging;
	private final UserRepository userRepository;

	public void sendNotificationByToken(NotificationDto notificationDto) throws FirebaseMessagingException {
		// User user = userRepository.findByUserId(notificationDto.getUserId()).orElseThrow(() -> new AuthenticationException(
		// 	ErrorCode.NOT_EXIST_ACCOUNT));

		Notification notification = Notification.builder()
			.setTitle(notificationDto.getTitle())
			.setBody(notificationDto.getBody())
			.build();

		Message message = Message.builder()
			.setToken(notificationDto.getToken())
			.setNotification(notification)
			.build();

		firebaseMessaging.send(message);
	}

}
