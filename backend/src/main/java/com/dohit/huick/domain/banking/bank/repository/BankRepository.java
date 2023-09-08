package com.dohit.huick.domain.banking.bank.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dohit.huick.domain.banking.bank.entity.Bank;

public interface BankRepository extends JpaRepository<Bank, Long> {
	Optional<Bank> findByBankId(Long bankId);

	Optional<Bank> findByBankCode(String bankCode);
}
