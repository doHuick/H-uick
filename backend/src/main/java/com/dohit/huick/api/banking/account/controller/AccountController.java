package com.dohit.huick.api.banking.account.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dohit.huick.api.banking.account.dto.AccountApiDto;
import com.dohit.huick.domain.banking.service.BankingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/banking/accounts")
@RequiredArgsConstructor
public class AccountController {
	private final BankingService bankingService;

	@GetMapping("/{userId}")
	public ResponseEntity<AccountApiDto.Response> getAccountByUserId(@PathVariable Long userId) {
		AccountApiDto.Response response = AccountApiDto.Response.from(bankingService.getAccountByUserId(userId));
		return ResponseEntity.ok().body(response);
	}
}
