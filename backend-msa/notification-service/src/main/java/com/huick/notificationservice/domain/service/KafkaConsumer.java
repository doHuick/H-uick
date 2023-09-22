package com.huick.notificationservice.domain.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.huick.notificationservice.domain.dto.NotificationDto;
import com.huick.notificationservice.domain.entity.Notification;
import com.huick.notificationservice.domain.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {
	private NotificationRepository notificationRepository;
	private NotificationService notificationService;


	@KafkaListener(topics = "create-notification")
	public void createNotification(String kafkaMessage) throws FirebaseMessagingException {
		log.info("Consumer Kafka Message : " + kafkaMessage);

		ObjectMapper mapper = new ObjectMapper();
		NotificationDto notificationDto = null;
		try {
			notificationDto = mapper.readValue(kafkaMessage, NotificationDto.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		if(notificationDto != null) {
			Notification notification = notificationRepository.save(Notification.from(notificationDto));
			notificationService.sendNotification(NotificationDto.from(notification));
		}
	}
}