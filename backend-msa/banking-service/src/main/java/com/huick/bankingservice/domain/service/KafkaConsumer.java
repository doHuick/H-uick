package com.huick.bankingservice.domain.service;

import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huick.bankingservice.domain.account.dto.AccountDto;
import com.huick.bankingservice.domain.account.entity.Account;
import com.huick.bankingservice.domain.autotransfer.dto.AutoTransferDto;
import com.huick.bankingservice.domain.autotransfer.entity.AutoTransfer;
import com.huick.bankingservice.domain.autotransfer.repository.AutoTransferRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {
	private AutoTransferRepository autoTransferRepository;

	@KafkaListener(topics = "create-autotransfer")
	public void createAutoTransfer(String kafkaMessage) {
		log.info("Consumer Kafka Message : " + kafkaMessage);

		ObjectMapper mapper = new ObjectMapper();
		AutoTransferDto autoTransferDto = null;
		try {
			autoTransferDto = mapper.readValue(kafkaMessage, AutoTransferDto.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		if(autoTransferDto != null) {
			autoTransferRepository.save(AutoTransfer.from(autoTransferDto));
		}
	}
}
