package com.dohit.huick.api.contract.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dohit.huick.api.contract.dto.ContractApiDto;
import com.dohit.huick.domain.contract.dto.ContractDto;
import com.dohit.huick.domain.contract.service.ContractService;
import com.dohit.huick.global.userinfo.UserInfo;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/contracts")
@RequiredArgsConstructor
public class ContractController {
	private final ContractService contractService;

	@PostMapping
	public ResponseEntity<Void> createContract(@RequestBody ContractApiDto.Request request) {
		contractService.createContract(ContractDto.from(request));
		return ResponseEntity.ok().build();
	}

	@GetMapping("/{contractId}")
	public ResponseEntity<ContractApiDto.Response> getContractByContractId(@PathVariable Long contractId) {
		return ResponseEntity.ok().body(ContractApiDto.Response.from(contractService.getContractByContractId(contractId)));
	}

	@GetMapping("/lessee/me")
	public ResponseEntity<List<ContractApiDto.Response>> getContractByLesseeId(@UserInfo Long lesseeId) {
		return ResponseEntity.ok().body(contractService.getContractByLesseeId(lesseeId).stream().map(
			ContractApiDto.Response::from).collect(
			Collectors.toList()));
	}
}
