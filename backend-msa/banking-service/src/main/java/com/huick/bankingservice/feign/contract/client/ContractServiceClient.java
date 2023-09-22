package com.huick.bankingservice.feign.contract.client;

import com.huick.bankingservice.feign.contract.dto.ContractApiDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "contract-service")
public interface ContractServiceClient {

    @GetMapping("/contracts/{contractId}")
    ContractApiDto.Response getContractByContractId(@PathVariable Long contractId);
}
