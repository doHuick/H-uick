package com.dohit.huick.domain.banking.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dohit.huick.domain.banking.account.service.AccountService;
import com.dohit.huick.domain.banking.bank.service.BankService;
import com.dohit.huick.domain.banking.transaction.service.TransactionService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BankingService {

	private final AccountService accountService;

	public void createAccount(Long userId) {
		accountService.createAccount(userId);
	}

}
