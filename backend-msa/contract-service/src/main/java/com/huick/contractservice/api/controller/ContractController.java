package com.huick.contractservice.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contracts")
public class ContractController {

	@GetMapping
	public ResponseEntity<String> testContract() {
		return ResponseEntity.ok().body("this is contract service");
	}
}
