package com.dohit.huick.domain.banking.account.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import com.dohit.huick.domain.banking.account.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	List<Account> findByUserId(Long userId);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Optional<Account> findByAccountNumber(String accountNumber);
}