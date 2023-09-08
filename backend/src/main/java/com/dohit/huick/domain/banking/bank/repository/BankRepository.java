package com.dohit.huick.domain.banking.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dohit.huick.domain.banking.bank.entity.Bank;

public interface BankRepository extends JpaRepository<Bank, Long> {
}
