package com.huick.bankingservice.domain.bank.dto;

import com.huick.bankingservice.domain.bank.constant.BankType;
import com.huick.bankingservice.domain.bank.entity.Bank;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BankDto {
	private Long bankId;
	private String bankCode;
	private BankType bankName;

	@Builder
	private BankDto(Long bankId, String bankCode, BankType bankName) {
		this.bankId = bankId;
		this.bankCode = bankCode;
		this.bankName = bankName;
	}

	public static BankDto from(Bank bank) {
		return BankDto.builder()
			.bankId(bank.getBankId())
			.bankCode(bank.getBankCode())
			.bankName(bank.getBankName())
			.build();
	}
}
