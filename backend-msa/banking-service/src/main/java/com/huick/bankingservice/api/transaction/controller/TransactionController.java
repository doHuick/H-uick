package com.huick.bankingservice.api.transaction.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huick.bankingservice.api.transaction.dto.TransactionApiDto;
import com.huick.bankingservice.domain.service.BankingService;
import com.huick.bankingservice.domain.transaction.dto.TransactionDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/banking/transactions")
@RequiredArgsConstructor
public class TransactionController {
	private final BankingService bankingService;

	@PostMapping
	public ResponseEntity<Void> transferMoney(@RequestBody TransactionApiDto.Request request) {
		bankingService.transferMoney(TransactionDto.from(request));
		return ResponseEntity.ok().build();
	}

	@GetMapping("/{userId}")
	public ResponseEntity<List<TransactionApiDto.Response>> getTransactionsByUserId(@PathVariable Long userId) {
		return ResponseEntity.ok(bankingService.getTransactionsByUserId(userId).stream().map(
			TransactionApiDto.Response::from).collect(
			Collectors.toList()));
	}
}
