package com.huick.bankingservice.api.repayment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huick.bankingservice.api.repayment.dto.RepaymentApiDto;
import com.huick.bankingservice.domain.service.BankingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/repayment")
@RequiredArgsConstructor
public class RepaymentController {
	private final BankingService bankingService;

	@PostMapping
	public ResponseEntity<Void> repay(@RequestBody RepaymentApiDto.Request request){
		// 페인 클라이언트
	 	bankingService.repay(request.getContractId(), request.getAmount());

		return ResponseEntity.ok().build();
	}
}
