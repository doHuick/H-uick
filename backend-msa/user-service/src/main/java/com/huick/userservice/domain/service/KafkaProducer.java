package com.huick.userservice.domain.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {
	private final KafkaTemplate<String, String> kafkaTemplate;

	public void createAccount(String topic, Long userId) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = "";
		try {
			System.out.println(userId);
			jsonInString = mapper.writeValueAsString(userId);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println(kafkaTemplate);
		kafkaTemplate.send(topic, jsonInString);
		log.info("Kafka Producer send data from the Contract microservice: " +  userId);
	}
}