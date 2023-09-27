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
import com.dohit.huick.domain.banking.repayment.dto.RepaymentDto;
import com.dohit.huick.domain.banking.repayment.service.RepaymentService;
import com.dohit.huick.domain.contract.dto.ContractDto;
import com.dohit.huick.domain.contract.service.ContractService;
import com.dohit.huick.domain.user.dto.UserDto;
import com.dohit.huick.domain.user.service.UserService;
import com.dohit.huick.global.userinfo.UserInfo;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/contracts")
@RequiredArgsConstructor
public class ContractController {
	private final ContractService contractService;
	private final UserService userService;
	private final RepaymentService repaymentService;

	@PostMapping
	public ResponseEntity<Void> createContract(@RequestBody ContractApiDto.Request request) {
		// 체결 이전 계약서가 생성된다.
		// 계약 정보와 만드는 사람의 정보가 필요하다.
		contractService.createContract(ContractDto.from(request));
		return ResponseEntity.ok().build();
	}

	@GetMapping("/{contractId}")
	public ResponseEntity<ContractApiDto.Response> getContractByContractId(@PathVariable Long contractId) {
		ContractDto contractDto = contractService.getContractByContractId(contractId);
		UserDto lesseeDto = userService.getUserByUserId(contractDto.getLesseeId());
		UserDto lessorDto = userService.getUserByUserId(contractDto.getLessorId());
		RepaymentDto repaymentDto = repaymentService.findCurrentRepaymentByContractId(contractId);
		int totalRepaymentCount = repaymentService.getRepaymentsByContractId(contractDto.getContractId())
			.size();

		return ResponseEntity.ok()
			.body(ContractApiDto.Response.of(contractService.getContractByContractId(contractId),
				lesseeDto, lessorDto, repaymentDto, totalRepaymentCount));
	}

	@GetMapping("/lessee/me")
	// 체결되지 않은 계약 처리해줘야함
	public ResponseEntity<List<ContractApiDto.Response>> getContractByLesseeId(@UserInfo Long lesseeId) {
		UserDto lesseeDto = userService.getUserByUserId(lesseeId);
		List<ContractApiDto.Response> response = contractService.getContractByLesseeId(lesseeId).stream()
			.map(contractDto -> {
				UserDto lessorDto = userService.getUserByUserId(contractDto.getLessorId());
				RepaymentDto repaymentDto = repaymentService.findCurrentRepaymentByContractId(
					contractDto.getContractId());
				int totalRepaymentCount = repaymentService.getRepaymentsByContractId(contractDto.getContractId())
					.size();

				return ContractApiDto.Response.of(contractDto, lesseeDto, lessorDto, repaymentDto,
					totalRepaymentCount);
			})
			.collect(Collectors.toList());

		return ResponseEntity.ok(response);
	}

	@GetMapping("/lessor/me")
	public ResponseEntity<List<ContractApiDto.Response>> getContractByLessorId(@UserInfo Long lessorId) {
		UserDto lessorDto = userService.getUserByUserId(lessorId);
		List<ContractApiDto.Response> response = contractService.getContractByLessorId(lessorId).stream()
			.map(contractDto -> {
				UserDto lesseeDto = userService.getUserByUserId(contractDto.getLesseeId());
				RepaymentDto repaymentDto = repaymentService.findCurrentRepaymentByContractId(
					contractDto.getContractId());
				int totalRepaymentCount = repaymentService.getRepaymentsByContractId(contractDto.getContractId())
					.size();
				return ContractApiDto.Response.of(contractDto, lesseeDto, lessorDto, repaymentDto,
					totalRepaymentCount);
			})
			.collect(Collectors.toList());

		return ResponseEntity.ok(response);
	}

	@PatchMapping("/status/{contractId}")
	public ResponseEntity<Void> updateContractStatus(@PathVariable Long contractId,
		@RequestBody ContractApiDto.Request request) {
		contractService.updateContractStatus(contractId, request.getStatus());
		repaymentService.createAllRepayment(contractService.getContractByContractId(contractId));
		return ResponseEntity.ok().build();
	}

	@PatchMapping("/{contractId}")
	public ResponseEntity<ContractApiDto.Response> updateFinalContract(@PathVariable Long contractId,
		@RequestBody ContractApiDto.Request request) throws IOException {
		ContractDto contractDto = contractService.updateFinalContract(contractId, ContractDto.from(request));
		// 스마트 컨트랙트 생성을 위해서 계약 정보 리턴
		return ResponseEntity.ok().body(ContractApiDto.Response.from(contractDto));
	}
}
