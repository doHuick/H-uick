package com.dohit.huick.api.banking;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dohit.huick.domain.banking.transaction.dto.TransactionDto;
import com.dohit.huick.domain.banking.service.BankingService;

import lombok.RequiredArgsConstructor;

@RestController("/banking/transactions")
@RequiredArgsConstructor
public class TransactionController {
	private final BankingService bankingService;

	@PostMapping
	public ResponseEntity<Void> transferMoney(@RequestBody TransactionApiDto.Request request) {
		bankingService.transferMoney(TransactionDto.from(request));
		return ResponseEntity.ok().build();
	}
}
