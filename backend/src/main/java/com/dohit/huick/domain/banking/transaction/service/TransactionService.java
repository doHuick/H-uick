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

	public void createTransaction(TransactionDto transactionDto) {
		transactionRepository.save(Transaction.from(transactionDto));
	}

	public List<TransactionDto> getTransactionsByUserId(Long userId) {
		return transactionRepository.findByUserId(userId).stream().map(TransactionDto::from).collect(Collectors.toList());
	}
}
