package com.dohit.huick.domain.banking.transaction.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dohit.huick.domain.banking.transaction.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	List<Transaction> findBySenderAccountNumberIsOrReceiverAccountNumberIs(String senderAccountNumber,
		String receiverAccountNumber);

	Optional<Transaction> findByTransactionId(Long transactionId);
}
