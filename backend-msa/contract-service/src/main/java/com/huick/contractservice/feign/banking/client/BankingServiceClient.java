package com.huick.contractservice.feign.banking.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.huick.contractservice.feign.banking.dto.AccountApiDto;

@FeignClient(name = "banking-service")
public interface BankingServiceClient {
	@GetMapping("/accounts/{userId}")
	AccountApiDto.Response getAccountByUserId(@PathVariable Long userId);
}
