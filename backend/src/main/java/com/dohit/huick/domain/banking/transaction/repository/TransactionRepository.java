package com.dohit.huick.domain.banking.transaction.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import com.dohit.huick.domain.banking.transaction.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	List<Transaction> findBySenderAccountNumberIsOrReceiverAccountNumberIs(String senderAccountNumber,
		String receiverAccountNumber);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Optional<Transaction> findByTransactionId(Long transactionId);
}
