package com.dohit.huick.api.contract.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
	public ResponseEntity<ContractApiDto.Response> createContract(@RequestBody ContractApiDto.Request request) {
		// 체결 이전 계약서가 생성된다.
		// 계약 정보와 만드는 사람의 정보가 필요하다.
		ContractDto contractDto = contractService.createContract(ContractDto.from(request));
		return ResponseEntity.ok().body(ContractApiDto.Response.from(contractDto));
	}

	@GetMapping("/{contractId}")
	public ResponseEntity<ContractApiDto.Response> getContractByContractId(@PathVariable Long contractId) {
		return ResponseEntity.ok().body(ContractApiDto.Response.from(contractService.getContractByContractId(contractId)));
	}

	@GetMapping("/lessee/me")
	// 체결되지 않은 계약 처리해줘야함
	public ResponseEntity<List<ContractApiDto.Response>> getContractByLesseeId(@UserInfo Long lesseeId) {
		return ResponseEntity.ok().body(contractService.getContractByLesseeId(lesseeId).stream().map(
			ContractApiDto.Response::from).collect(
			Collectors.toList()));
	}

	@GetMapping("/lessor/me")
	public ResponseEntity<List<ContractApiDto.Response>> getContractByLessorId(@UserInfo Long lessorId) {
		return ResponseEntity.ok().body(contractService.getContractByLessorId(lessorId).stream().map(
			ContractApiDto.Response::from).collect(
			Collectors.toList()));
	}

	@PatchMapping("/status/{contractId}")
	public ResponseEntity<Void> updateContractStatus(@PathVariable Long contractId, @RequestBody ContractApiDto.Request request) {
		contractService.updateContractStatus(contractId, request.getStatus());
		return ResponseEntity.ok().build();
	}

	@PatchMapping("/{contractId}")
	public ResponseEntity<ContractApiDto.Response> updateFinalContract(@PathVariable Long contractId, @RequestBody ContractApiDto.Request request) throws IOException {
		ContractDto contractDto = contractService.updateFinalContract(contractId, ContractDto.from(request));
		// 스마트 컨트랙트 생성을 위해서 계약 정보 리턴
		return ResponseEntity.ok().body(ContractApiDto.Response.from(contractDto));
	}
}
