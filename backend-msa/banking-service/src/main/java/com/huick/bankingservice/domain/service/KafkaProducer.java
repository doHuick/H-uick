package com.huick.bankingservice.domain.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huick.bankingservice.domain.autotransfer.dto.AutoTransferDto;
import com.huick.bankingservice.domain.dto.ContractDto;
import com.huick.bankingservice.domain.dto.NotificationDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {
	private KafkaTemplate<String, String> kafkaTemplate;

	public ContractDto updateContractStatus(String topic, ContractDto contractDto) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = "";
		try {
			jsonInString = mapper.writeValueAsString(contractDto);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		kafkaTemplate.send(topic, jsonInString);
		log.info("Kafka Producer send data from the Contract microservice: " +  contractDto);

		return contractDto;
	}

	public NotificationDto createNotification(String topic, NotificationDto notificationDto) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = "";
		try {
			jsonInString = mapper.writeValueAsString(notificationDto);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		kafkaTemplate.send(topic, jsonInString);
		log.info("Kafka Producer send data from the Contract microservice: " +  notificationDto);

		return notificationDto;
	}
}
