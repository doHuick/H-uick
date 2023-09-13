package com.dohit.huick.api.banking.repayment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dohit.huick.api.banking.repayment.dto.RepaymentApiDto;
import com.dohit.huick.domain.banking.service.BankingService;
import com.dohit.huick.domain.contract.service.ContractService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/banking/repayment")
@RequiredArgsConstructor
public class RepaymentController {
	private final BankingService bankingService;
	private final ContractService contractService;

	@PostMapping
	public ResponseEntity<Void> repay(@RequestBody RepaymentApiDto.Request request){
		bankingService.repay(contractService.getContractByContractId(request.getContractId()), request.getAmount());

		return ResponseEntity.ok().build();
	}
}
