package com.huick.userservice.feign.banking.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.huick.userservice.feign.banking.dto.AccountApiDto;

@FeignClient(name = "banking-service")
public interface BankingServiceClient {

    @GetMapping("/accounts/{userId}")
    AccountApiDto.Response getAccountByUserId(@PathVariable Long userId);
}