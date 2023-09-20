package com.huick.bankingservice.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/banking")
public class BankingController {

	@GetMapping
	public ResponseEntity<String> testBanking() {
		return ResponseEntity.ok().body("this is banking service");
	}
}
