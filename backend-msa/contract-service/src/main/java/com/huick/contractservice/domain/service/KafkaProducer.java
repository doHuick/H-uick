package com.huick.contractservice.domain.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huick.contractservice.domain.dto.AutoTransferDto;
import com.huick.contractservice.domain.dto.TransactionDto;
import com.huick.contractservice.domain.repository.ContractRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {
	private KafkaTemplate<String, String> kafkaTemplate;

	public AutoTransferDto createAutoTransfer(String topic, AutoTransferDto autoTransferDto) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = "";
		try {
			jsonInString = mapper.writeValueAsString(autoTransferDto);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		kafkaTemplate.send(topic, jsonInString);
		log.info("Kafka Producer send data from the Contract microservice: " +  autoTransferDto);

		return autoTransferDto;
	}

	public TransactionDto transferMoney(String topic, TransactionDto transactionDto) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = "";
		try {
			jsonInString = mapper.writeValueAsString(transactionDto);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		kafkaTemplate.send(topic, jsonInString);
		log.info("Kafka Producer send data from the Contract microservice: " +  transactionDto);

		return transactionDto;
	}
}
