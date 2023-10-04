package com.dohit.huick.domain.banking.transaction.dto;

import java.time.LocalDateTime;

import com.dohit.huick.api.banking.transaction.dto.TransactionApiDto;
import com.dohit.huick.domain.banking.transaction.entity.Transaction;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TransactionDto {
	private Long transactionId;
	private String senderAccountNumber;
	private String receiverAccountNumber;
	private Long senderId;
	private Long receiverId;
	private Long amount;
	private LocalDateTime transactionTime;

	@Builder
	private TransactionDto(Long transactionId, String senderAccountNumber, String receiverAccountNumber, Long senderId,
		Long receiverId, Long amount, LocalDateTime transactionTime) {
		this.transactionId = transactionId;
		this.senderAccountNumber = senderAccountNumber;
		this.receiverAccountNumber = receiverAccountNumber;
		this.senderId = senderId;
		this.receiverId = receiverId;
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
			.senderId(request.getSenderId())
			.receiverId(request.getReceiverId())
			.amount(request.getAmount())
			.build();
	}

	public static TransactionDto from(TransactionDto transactionDto, String senderAccountNumber, String receiverAccountNumber) {
		return TransactionDto.builder()
			.senderAccountNumber(senderAccountNumber)
			.receiverAccountNumber(receiverAccountNumber)
			.amount(transactionDto.getAmount())
			.build();
	}
}
