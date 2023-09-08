package com.dohit.huick.domain.banking.bank.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dohit.huick.domain.banking.bank.dto.BankDto;
import com.dohit.huick.domain.banking.bank.entity.Bank;
import com.dohit.huick.domain.banking.bank.repository.BankRepository;
import com.dohit.huick.global.error.ErrorCode;
import com.dohit.huick.global.error.exception.BankingException;

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
