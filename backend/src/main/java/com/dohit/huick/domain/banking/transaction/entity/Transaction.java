package com.dohit.huick.domain.banking.transaction.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.dohit.huick.domain.banking.transaction.dto.TransactionDto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long transactionId;

	@Column(nullable = false)
	private String senderAccountNumber;

	@Column(nullable = false)
	private String receiverAccountNumber;

	@Column(nullable = false)
	private Long amount;

	@CreatedDate
	private LocalDateTime transactionTime;

	@Builder
	private Transaction(Long transactionId, String senderAccountNumber, String receiverAccountNumber, Long amount,
		LocalDateTime transactionTime) {
		this.transactionId = transactionId;
		this.senderAccountNumber = senderAccountNumber;
		this.receiverAccountNumber = receiverAccountNumber;
		this.amount = amount;
		this.transactionTime = transactionTime;
	}

	public static Transaction from(TransactionDto transactionDto) {
		return Transaction.builder()
			.senderAccountNumber(transactionDto.getSenderAccountNumber())
			.receiverAccountNumber(transactionDto.getReceiverAccountNumber())
			.amount(transactionDto.getAmount())
			.build();
	}
}
