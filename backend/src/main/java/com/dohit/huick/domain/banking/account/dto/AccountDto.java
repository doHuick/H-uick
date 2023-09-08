package com.dohit.huick.domain.banking.account.dto;

import java.time.LocalDateTime;

import com.dohit.huick.domain.banking.bank.dto.BankDto;
import com.dohit.huick.domain.banking.bank.constant.BankType;
import com.dohit.huick.domain.banking.account.entity.Account;

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

	public static AccountDto of(AccountDto account, BankDto bank) {
		return AccountDto.builder()
			.accountId(account.getAccountId())
			.accountNumber(account.getAccountNumber())
			.bankCode(account.getBankCode())
			.bankName(bank.getBankName())
			.balance(account.getBalance())
			.createdTime(account.getCreatedTime())
			.build();
	}

	public static AccountDto from(Account account) {
		return AccountDto.builder()
			.accountId(account.getAccountId())
			.accountNumber(account.getAccountNumber())
			.bankCode(account.getBankCode())
			.balance(account.getBalance())
			.createdTime(account.getCreatedTime())
			.build();
	}
}
