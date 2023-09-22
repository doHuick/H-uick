package com.huick.notificationservice.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.huick.notificationservice.domain.constant.NotificationType;
import com.huick.notificationservice.domain.dto.DeviceTokenDto;
import com.huick.notificationservice.domain.dto.NotificationDto;
import com.huick.notificationservice.domain.entity.DeviceToken;
import com.huick.notificationservice.domain.repository.DeviceTokenRepository;
import com.huick.notificationservice.domain.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationService {
	private final FirebaseMessaging firebaseMessaging;
	private final NotificationRepository notificationRepository;
	private final DeviceTokenRepository deviceTokenRepository;

	public void sendNotification(NotificationDto notificationDto) throws FirebaseMessagingException {
		List<DeviceToken> deviceToken = deviceTokenRepository.findByUserId(notificationDto.getUserId());

		if(notificationDto.getNotificationType() == NotificationType.UPCOMING_TRANSFER) {
			Notification notification = Notification.builder()
				.setTitle("이체 예정")
				.setBody("이체 예정")
				.build();

			deviceToken.forEach(d -> {
				try {
					firebaseMessaging.send(Message.builder()
						.setToken(d.getDeviceToken())
						.setNotification(notification)
						.build());
				} catch (FirebaseMessagingException e) {
					throw new RuntimeException(e);
				}
			});
		}
		else if(notificationDto.getNotificationType() == NotificationType.TRANSFER_SUCCESS) {
			Notification notification = Notification.builder()
				.setTitle("이체 성공")
				.setBody("이체 성공")
				.build();

			deviceToken.forEach(d -> {
				try {
					firebaseMessaging.send(Message.builder()
						.setToken(d.getDeviceToken())
						.setNotification(notification)
						.build());
				} catch (FirebaseMessagingException e) {
					throw new RuntimeException(e);
				}
			});
		}
		else if(notificationDto.getNotificationType() == NotificationType.OVERDUE) {
			Notification notification = Notification.builder()
				.setTitle("연체")
				.setBody("연체")
				.build();

			deviceToken.forEach(d -> {
				try {
					firebaseMessaging.send(Message.builder()
						.setToken(d.getDeviceToken())
						.setNotification(notification)
						.build());
				} catch (FirebaseMessagingException e) {
					throw new RuntimeException(e);
				}
			});
		}
	}

	public NotificationDto createNotification(NotificationDto notificationDto) {
		return NotificationDto.from(notificationRepository.save(com.huick.notificationservice.domain.entity.Notification.from(notificationDto)));
	}

	public void createDeviceToken(DeviceTokenDto deviceTokenDto) {
		deviceTokenRepository.save(DeviceToken.from(deviceTokenDto));
	}

	public List<DeviceTokenDto> getDeviceTokenByUserId(Long userId) {
		return deviceTokenRepository.findByUserId(userId).stream().map(DeviceTokenDto::from).collect(Collectors.toList());
	}

	public List<NotificationDto> getNotificationUserId(Long userId) {
		return notificationRepository.findByUserId(userId).stream().map(NotificationDto::from).collect(Collectors.toList());
	}
}
