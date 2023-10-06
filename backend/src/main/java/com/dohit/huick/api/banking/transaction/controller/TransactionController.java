package com.dohit.huick.api.banking.transaction.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dohit.huick.api.banking.transaction.dto.TransactionApiDto;
import com.dohit.huick.domain.banking.service.BankingService;
import com.dohit.huick.domain.banking.transaction.dto.TransactionDto;
import com.dohit.huick.global.userinfo.UserInfo;

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

	@GetMapping("/me")
	public ResponseEntity<List<TransactionApiDto.Response>> getTransactionsByUserId(@UserInfo Long userId) {
		return ResponseEntity.ok(bankingService.getTransactionsByUserId(userId).stream().map(
			TransactionApiDto.Response::from).collect(
			Collectors.toList()));
	}
}
