package com.huick.userservice.domain.dto;

import java.time.LocalDateTime;

import com.huick.userservice.domain.constant.BankType;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AccountDto {
	private Long accountId;
	private String accountNumber;
	private String bankCode;
	private BankType bankName;
	private Long balance;
	private LocalDateTime createdTime;

	@Builder
	private AccountDto(Long accountId, String accountNumber, String bankCode, BankType bankName, Long balance,
		LocalDateTime createdTime) {
		this.accountId = accountId;
		this.accountNumber = accountNumber;
		this.bankCode = bankCode;
		this.bankName = bankName;
		this.balance = balance;
		this.createdTime = createdTime;
	}
}
