package com.huick.bankingservice.domain.bank.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huick.bankingservice.domain.bank.dto.BankDto;
import com.huick.bankingservice.domain.bank.entity.Bank;
import com.huick.bankingservice.domain.bank.repository.BankRepository;
import com.huick.bankingservice.global.error.ErrorCode;
import com.huick.bankingservice.global.error.exception.BankingException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BankService {
	private final BankRepository bankRepository;

	public BankDto getBankByBankId(Long bankId) {
		Optional<Bank> bank = bankRepository.findByBankId(bankId);
		return BankDto.from(bank.orElseThrow(() -> new BankingException(ErrorCode.NOT_EXIST_BANK)));
	}
	public BankDto getBankByBankCode(String bankCode) {
		Optional<Bank> bank = bankRepository.findByBankCode(bankCode);
		return BankDto.from(bank.orElseThrow(() -> new BankingException(ErrorCode.NOT_EXIST_BANK)));
	}
}
