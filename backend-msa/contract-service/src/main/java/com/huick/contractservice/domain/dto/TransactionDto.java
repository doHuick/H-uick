package com.huick.contractservice.domain.dto;

import java.time.LocalDateTime;

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
	public static TransactionDto of(String senderAccountNumber, String receiverAccountNumber, Long amount) {
		return TransactionDto.builder()
			.senderAccountNumber(senderAccountNumber)
			.receiverAccountNumber(receiverAccountNumber)
			.amount(amount)
			.build();
	}
}
