package com.dohit.huick.domain.banking.account.service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dohit.huick.domain.banking.account.dto.AccountDto;
import com.dohit.huick.domain.banking.account.entity.Account;
import com.dohit.huick.domain.banking.account.repository.AccountRepository;
import com.dohit.huick.global.error.ErrorCode;
import com.dohit.huick.global.error.exception.BankingException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

	private final AccountRepository accountRepository;

	public List<AccountDto> getAccountsByUserId(Long userId) {
		List<Account> accounts = accountRepository.findByUserId(userId);
		if (accounts == null || accounts.size() == 0) {
			throw new BankingException(ErrorCode.NO_ACCOUNT_EXIST);
		}

		return accounts.stream().map(AccountDto::from).collect(Collectors.toList());
	}

	public AccountDto getAccountByAccountNumber(String accountNumber) {
		Optional<Account> account = accountRepository.findByAccountNumber(accountNumber);
		return AccountDto.from(account.orElseThrow(() -> new BankingException(ErrorCode.NOT_EXIST_ACCOUNT)));
	}

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	public void createAccount(Long userId) {

		SecureRandom secureRandom = new SecureRandom();

		while (true) {
			StringBuilder stringBuilder = new StringBuilder("110408");
			stringBuilder.append(secureRandom.nextInt(900000) + 100000);
			try {
				accountRepository.save(Account.of(stringBuilder.toString(), userId, "001", 0L));
				return;
			} catch (Exception ignored) {
			}
		}
	}

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	public void updateBalance(String accountNumber, Long money) {
		Optional<Account> optionalAccount = accountRepository.findByAccountNumber(accountNumber);

		Account account = optionalAccount.orElseThrow(() -> new BankingException(ErrorCode.NO_ACCOUNT_EXIST));

		account.updateBalance(account.getBalance() + money);
	}
}
