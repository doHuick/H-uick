package com.huick.contractservice.domain.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huick.contractservice.domain.dto.ContractDto;
import com.huick.contractservice.domain.entity.Contract;
import com.huick.contractservice.domain.repository.ContractRepository;
import com.huick.contractservice.global.error.ErrorCode;
import com.huick.contractservice.global.error.exception.ContractException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {
	private ContractRepository contractRepository;

	@KafkaListener(topics = "update-contractstatus")
	public void updateContractStatus(String kafkaMessage) {
		log.info("Consumer Kafka Message : " + kafkaMessage);

		ObjectMapper mapper = new ObjectMapper();
		ContractDto contractDto = null;
		try {
			contractDto = mapper.readValue(kafkaMessage, ContractDto.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		if(contractDto != null) {
			Contract contract = contractRepository.findByContractId(contractDto.getContractId()).orElseThrow(() -> new ContractException(
				ErrorCode.NOT_EXIST_CONTRACT));

			contract.updateStatus(contractDto.getStatus());

		}
	}
}
