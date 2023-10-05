package com.dohit.huick.domain.notification.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dohit.huick.domain.notification.constant.NotificationType;
import com.dohit.huick.domain.notification.dto.DeviceTokenDto;
import com.dohit.huick.domain.notification.dto.NotificationDto;
import com.dohit.huick.domain.notification.entity.DeviceToken;
import com.dohit.huick.domain.notification.repository.DeviceTokenRepository;
import com.dohit.huick.domain.notification.repository.NotificationRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class NotificationService {
	private final FirebaseMessaging firebaseMessaging;
	private final NotificationRepository notificationRepository;
	private final DeviceTokenRepository deviceTokenRepository;

	public void sendNotification(NotificationDto notificationDto) throws FirebaseMessagingException {
		List<DeviceToken> deviceToken = deviceTokenRepository.findByUserId(notificationDto.getUserId());

		if (notificationDto.getNotificationType() == NotificationType.UPCOMING_TRANSFER) {
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
		} else if (notificationDto.getNotificationType() == NotificationType.TRANSFER_SUCCESS) {
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
		} else if (notificationDto.getNotificationType() == NotificationType.OVERDUE) {
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
		return NotificationDto.from(
			notificationRepository.save(com.dohit.huick.domain.notification.entity.Notification.from(notificationDto)));
	}

	public void createDeviceToken(DeviceTokenDto deviceTokenDto) {
		deviceTokenRepository.save(DeviceToken.from(deviceTokenDto));
	}

	public List<DeviceTokenDto> getDeviceTokenByUserId(Long userId) {
		return deviceTokenRepository.findByUserId(userId)
			.stream()
			.map(DeviceTokenDto::from)
			.collect(Collectors.toList());
	}

	public List<NotificationDto> getNotificationUserId(Long userId) {
		return notificationRepository.findByUserId(userId)
			.stream()
			.map(NotificationDto::from)
			.collect(Collectors.toList());
	}

	public void sendNotificationDirectly(NotificationDto notificationDto) {
		Notification notification = Notification.builder()
			.setTitle(notificationDto.getTitle())
			.setBody(notificationDto.getBody())
			.build();

		deviceTokenRepository.findByUserId(notificationDto.getUserId())
			.forEach(deviceToken -> {
				System.out.println(deviceToken.getDeviceToken());
				Message message = Message.builder()
					.setToken(deviceToken.getDeviceToken())
					.setNotification(notification)
					.build();

				try {
					firebaseMessaging.send(message);
					System.out.println("sent");
				} catch (FirebaseMessagingException e) {
					e.printStackTrace();
				}
			});

	}
}
