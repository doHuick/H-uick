package com.huick.contractservice.feign.banking.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.huick.contractservice.domain.constant.BankType;

import lombok.Builder;
import lombok.Getter;

public class AccountApiDto {
	@Getter
	@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class Response {
		private Long accountId;
		private String accountNumber;
		private String bankCode;
		private BankType bankName;
		private Long balance;
		private LocalDateTime createdTime;

		@Builder
		private Response(Long accountId, String accountNumber, String bankCode, BankType bankName, Long balance,
			LocalDateTime createdTime) {
			this.accountId = accountId;
			this.accountNumber = accountNumber;
			this.bankCode = bankCode;
			this.bankName = bankName;
			this.balance = balance;
			this.createdTime = createdTime;
		}
	}
}
