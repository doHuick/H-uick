package com.dohit.huick.domain.banking.transaction.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dohit.huick.domain.banking.transaction.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	List<Transaction> findByUserId(Long userId);
}
