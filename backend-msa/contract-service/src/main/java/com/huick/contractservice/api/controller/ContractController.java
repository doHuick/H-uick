package com.huick.contractservice.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.Path;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.huick.contractservice.api.dto.ContractApiDto;
import com.huick.contractservice.domain.dto.ContractDto;
import com.huick.contractservice.domain.service.ContractService;

import lombok.RequiredArgsConstructor;

@RestController
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

	@GetMapping("/lessee/{lesseeId}")
	public ResponseEntity<List<ContractApiDto.Response>> getContractByLesseeId(@PathVariable Long lesseeId) {
		return ResponseEntity.ok().body(contractService.getContractByLesseeId(lesseeId).stream().map(
			ContractApiDto.Response::from).collect(
			Collectors.toList()));
	}

	@GetMapping("/lessor/{lesseeId}")
	public ResponseEntity<List<ContractApiDto.Response>> getContractByLessorId(@PathVariable Long lessorId) {
		return ResponseEntity.ok().body(contractService.getContractByLessorId(lessorId).stream().map(
			ContractApiDto.Response::from).collect(
			Collectors.toList()));
	}

	@PatchMapping("/status/{contractId}")
	public ResponseEntity<Void> updateContractStatus(@PathVariable Long contractId, @RequestBody ContractApiDto.Request request) {
		contractService.updateContractStatus(contractId, request.getStatus());
		return ResponseEntity.ok().build();
	}
}
