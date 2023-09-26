package com.huick.bankingservice.domain.service;

import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huick.bankingservice.domain.account.dto.AccountDto;
import com.huick.bankingservice.domain.account.entity.Account;
import com.huick.bankingservice.domain.account.repository.AccountRepository;
import com.huick.bankingservice.domain.autotransfer.dto.AutoTransferDto;
import com.huick.bankingservice.domain.autotransfer.entity.AutoTransfer;
import com.huick.bankingservice.domain.autotransfer.repository.AutoTransferRepository;
import com.huick.bankingservice.domain.transaction.dto.TransactionDto;
import com.huick.bankingservice.domain.transaction.entity.Transaction;
import com.huick.bankingservice.domain.transaction.repository.TransactionRepository;
import com.huick.bankingservice.global.error.ErrorCode;
import com.huick.bankingservice.global.error.exception.BankingException;
import com.huick.bankingservice.global.error.exception.TransferException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {
	private AutoTransferRepository autoTransferRepository;
	private AccountRepository accountRepository;
	private TransactionRepository transactionRepository;

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

	@KafkaListener(topics = "transfer-money")
	public void transferMoney(String kafkaMessage) {
		log.info("Consumer Kafka Message : " + kafkaMessage);

		ObjectMapper mapper = new ObjectMapper();
		TransactionDto transactionDto = null;
		try {
			transactionDto = mapper.readValue(kafkaMessage, TransactionDto.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		// from 계좌 가져옴
		AccountDto senderAccountDto = AccountDto.from(accountRepository.findByAccountNumber(transactionDto.getSenderAccountNumber()).orElseThrow(() -> new BankingException(ErrorCode.NOT_EXIST_ACCOUNT)));
		if (senderAccountDto.getBalance() < transactionDto.getAmount()) {
			throw new TransferException(ErrorCode.NOT_ENOUGH_MONEY);
		}

		// from 계좌에서 돈을 뺌
		accountRepository.findByAccountNumber(senderAccountDto.getAccountNumber()).orElseThrow(() -> new BankingException(ErrorCode.NOT_EXIST_ACCOUNT)).updateBalance(
			senderAccountDto.getBalance() - transactionDto.getAmount());

		// to 계좌  가져와서 돈을 넣음
		AccountDto receiverAccountDto = AccountDto.from(accountRepository.findByAccountNumber(transactionDto.getReceiverAccountNumber()).orElseThrow(() -> new BankingException(ErrorCode.NOT_EXIST_ACCOUNT)));
		accountRepository.findByAccountNumber(receiverAccountDto.getAccountNumber()).orElseThrow(() -> new BankingException(ErrorCode.NOT_EXIST_ACCOUNT)).updateBalance(
			receiverAccountDto.getBalance() + transactionDto.getAmount());

		// 트랜잭션 데이터를 생성함
		transactionRepository.save(Transaction.from(transactionDto));
		return;
	}
}
