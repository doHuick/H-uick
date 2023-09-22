package com.huick.userservice.domain.dto;

import java.time.LocalDateTime;

import com.huick.userservice.domain.constant.BankType;

import com.huick.userservice.feign.banking.dto.AccountApiDto;
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

	public static AccountDto from(AccountApiDto.Response response) {
		return AccountDto.builder()
				.accountId(response.getAccountId())
				.accountNumber(response.getAccountNumber())
				.bankCode(response.getBankCode())
				.bankName(response.getBankName())
				.balance(response.getBalance())
				.createdTime(response.getCreatedTime())
				.build();
	}
}
