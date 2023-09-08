package com.dohit.huick.domain.banking.account.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dohit.huick.domain.banking.account.dto.AccountDto;
import com.dohit.huick.domain.banking.account.entity.Account;
import com.dohit.huick.domain.banking.account.repository.AccountRepository;
import com.dohit.huick.global.error.ErrorCode;
import com.dohit.huick.global.error.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

	private final AccountRepository accountRepository;

	public List<AccountDto> getAccountsByUserId(Long userId){
		List<Account> accounts = accountRepository.findByUserId(userId);
		if(accounts.size() == 0) {
			throw new BusinessException(ErrorCode.NO_ACCOUNT_EXIST);
		}

		return accounts.stream().map(AccountDto::from).collect(Collectors.toList());
	}

	public AccountDto getAccountByAccountNumber(String accountNumber) {
		Optional<Account> account = accountRepository.findByAccountNumber(accountNumber);
		if(account.isEmpty()) {
			throw new BusinessException(ErrorCode.NO_ACCOUNT_EXIST);
		}
		return AccountDto.from(account.get());
	}
}
