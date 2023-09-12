package com.dohit.huick.domain.banking.transaction.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dohit.huick.domain.banking.transaction.dto.TransactionDto;
import com.dohit.huick.domain.banking.transaction.entity.Transaction;
import com.dohit.huick.domain.banking.transaction.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionService {

	private final TransactionRepository transactionRepository;

	public Long createTransaction(TransactionDto transactionDto) {
		Transaction transaction = transactionRepository.save(Transaction.from(transactionDto));
		return transaction.getTransactionId();
	}

	public List<TransactionDto> getTransactionsByUserId(String accountNumber) {
		return transactionRepository.findBySenderAccountNumberIsOrReceiverAccountNumberIs(accountNumber, accountNumber).stream().map(TransactionDto::from).collect(Collectors.toList());
	}
}
