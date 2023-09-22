package com.huick.bankingservice.domain.bank.repository;

import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import com.huick.bankingservice.domain.bank.entity.Bank;

public interface BankRepository extends JpaRepository<Bank, Long> {
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Optional<Bank> findByBankId(Long bankId);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Optional<Bank> findByBankCode(String bankCode);
}
