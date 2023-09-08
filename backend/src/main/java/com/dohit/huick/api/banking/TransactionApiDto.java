package com.dohit.huick.api.banking;

import java.time.LocalDateTime;

import com.dohit.huick.domain.banking.transaction.dto.TransactionDto;

import lombok.Builder;
import lombok.Getter;


public class TransactionApiDto {
	@Getter
	public static class Request {
		private String senderAccountNumber;
		private String receiverAccountNumber;
		private Long amount;

		@Builder
		public Request(String senderAccountNumber, String receiverAccountNumber, Long amount) {
			this.senderAccountNumber = senderAccountNumber;
			this.receiverAccountNumber = receiverAccountNumber;
			this.amount = amount;
		}
	}

	@Getter
	public static class Response {
		private Long transactionId;
		private String senderAccountNumber;
		private String receiverAccountNumber;
		private Long amount;
		private LocalDateTime transactionTime;

		@Builder
		public Response(Long transactionId, String senderAccountNumber, String receiverAccountNumber, Long amount,
			LocalDateTime transactionTime) {
			this.transactionId = transactionId;
			this.senderAccountNumber = senderAccountNumber;
			this.receiverAccountNumber = receiverAccountNumber;
			this.amount = amount;
			this.transactionTime = transactionTime;
		}

		public static Response from(TransactionDto transactionDto) {
			return Response.builder()
				.transactionId(transactionDto.getTransactionId())
				.senderAccountNumber(transactionDto.getSenderAccountNumber())
				.receiverAccountNumber(transactionDto.getReceiverAccountNumber())
				.amount(transactionDto.getAmount())
				.transactionTime(transactionDto.getTransactionTime())
				.build();
		}
	}
}
