package com.dohit.huick.api.banking.transaction.dto;

import java.time.LocalDateTime;

import com.dohit.huick.domain.banking.transaction.dto.TransactionDto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TransactionApiDto {
	@Getter
	@NoArgsConstructor
	@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class Request {
		private Long senderId;
		private Long receiverId;
		private Long amount;

		@Builder
		private Request(Long senderId, Long receiverId, Long amount) {
			this.senderId = senderId;
			this.receiverId = receiverId;
			this.amount = amount;
		}
	}

	@Getter
	@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class Response {
		private Long transactionId;
		private String senderId;
		private String receiverId;
		private Long amount;
		private LocalDateTime transactionTime;

		@Builder
		private Response(Long transactionId, String senderId, String receiverId, Long amount,
			LocalDateTime transactionTime) {
			this.transactionId = transactionId;
			this.senderId = senderId;
			this.receiverId = receiverId;
			this.amount = amount;
			this.transactionTime = transactionTime;
		}

		public static Response from(TransactionDto transactionDto) {
			return Response.builder()
				.transactionId(transactionDto.getTransactionId())
				.senderId(transactionDto.getSenderAccountNumber())
				.receiverId(transactionDto.getReceiverAccountNumber())
				.amount(transactionDto.getAmount())
				.transactionTime(transactionDto.getTransactionTime())
				.build();
		}
	}
}
