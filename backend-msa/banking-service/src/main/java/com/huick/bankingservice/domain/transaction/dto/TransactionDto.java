package com.huick.bankingservice.domain.transaction.dto;

import java.time.LocalDateTime;

import com.huick.bankingservice.api.transaction.dto.TransactionApiDto;
import com.huick.bankingservice.domain.transaction.entity.Transaction;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TransactionDto {
	private Long transactionId;
	private String senderAccountNumber;
	private String receiverAccountNumber;
	private Long amount;
	private LocalDateTime transactionTime;

	@Builder
	private TransactionDto(Long transactionId, String senderAccountNumber, String receiverAccountNumber, Long amount,
		LocalDateTime transactionTime) {
		this.transactionId = transactionId;
		this.senderAccountNumber = senderAccountNumber;
		this.receiverAccountNumber = receiverAccountNumber;
		this.amount = amount;
		this.transactionTime = transactionTime;
	}

	public static TransactionDto from(Transaction transaction) {
		return TransactionDto.builder()
			.transactionId(transaction.getTransactionId())
			.senderAccountNumber(transaction.getSenderAccountNumber())
			.receiverAccountNumber(transaction.getReceiverAccountNumber())
			.amount(transaction.getAmount())
			.transactionTime(transaction.getTransactionTime())
			.build();
	}

	public static TransactionDto of(String senderAccountNumber, String receiverAccountNumber, Long amount) {
		return TransactionDto.builder()
			.senderAccountNumber(senderAccountNumber)
			.receiverAccountNumber(receiverAccountNumber)
			.amount(amount)
			.build();
	}

	public static TransactionDto from(TransactionApiDto.Request request) {
		return TransactionDto.builder()
			.senderAccountNumber(request.getSenderAccountNumber())
			.receiverAccountNumber(request.getReceiverAccountNumber())
			.amount(request.getAmount())
			.build();
	}
}
