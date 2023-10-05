package com.dohit.huick.api.contract.controller;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
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
import com.dohit.huick.domain.banking.repayment.constant.RepaymentStatus;
import com.dohit.huick.domain.banking.repayment.dto.RepaymentDto;
import com.dohit.huick.domain.banking.repayment.service.RepaymentService;
import com.dohit.huick.domain.contract.constant.ContractStatus;
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
	public ResponseEntity<ContractApiDto.Response> createContract(@RequestBody ContractApiDto.Request request) {
		// 체결 이전 계약서가 생성된다.
		// 계약 정보와 만드는 사람의 정보가 필요하다.
		ContractDto contractDto = contractService.createContract(ContractDto.from(request));
		return ResponseEntity.ok().body(ContractApiDto.Response.from(contractDto));
	}

	@GetMapping("/{contractId}")
	public ResponseEntity<ContractApiDto.Response> getContractByContractId(@PathVariable Long contractId) {
		ContractDto contractDto = contractService.getContractByContractId(contractId);
		UserDto lesseeDto = null;
		UserDto lessorDto = null;
		if (contractDto.getLesseeId() != null) {
			lesseeDto = userService.getUserByUserId(contractDto.getLesseeId());
		} else {
			lesseeDto = UserDto.from("", "");
		}
		if (contractDto.getLessorId() != null) {
			lessorDto = userService.getUserByUserId(contractDto.getLessorId());
		} else {
			lessorDto = UserDto.from("", "", "", "");
		}
		RepaymentDto repaymentDto = repaymentService.findTopUnpaidRepaymentByContractId(contractId);

		if (repaymentDto == null)
			repaymentDto = RepaymentDto.of(0L, 0, null);

		int totalRepaymentCount = repaymentService.getRepaymentsByContractId(contractDto.getContractId())
			.size();

		int paidCount = repaymentService.countRepaymentsByContractIdAndStatus(contractId, RepaymentStatus.PAID);

		Long balance = repaymentService.calculateBalance(contractId, RepaymentStatus.UNPAID);

		return ResponseEntity.ok()
			.body(ContractApiDto.Response.of(contractService.getContractByContractId(contractId),
				lesseeDto, lessorDto, repaymentDto, totalRepaymentCount, paidCount, balance));
	}

	@GetMapping("/me")
	public ResponseEntity<List<ContractApiDto.Response>> getContractsByUserId(@UserInfo Long userId) {
		UserDto userDto = userService.getUserByUserId(userId);
		List<ContractApiDto.Response> response = contractService.getContractsByUserId(userId).stream()
			.filter(contractDto -> contractDto.getStatus() == ContractStatus.EXECUTION_COMPLETED)
			.map(contractDto -> {
				RepaymentDto repaymentDto = repaymentService.findTopUnpaidRepaymentByContractId(
					contractDto.getContractId());
				int totalRepaymentCount = repaymentService.getRepaymentsByContractId(contractDto.getContractId())
					.size();

				if (Objects.equals(contractDto.getLesseeId(), userId)) {
					return ContractApiDto.Response.of(contractDto, userDto, userService.getUserByUserId(
							contractDto.getLessorId()), repaymentDto,
						totalRepaymentCount);
				} else {
					return ContractApiDto.Response.of(contractDto, userService.getUserByUserId(
							contractDto.getLesseeId()), userDto, repaymentDto,
						totalRepaymentCount);
				}
			})
			.collect(Collectors.toList());

		return ResponseEntity.ok(response);
	}

	@GetMapping("/lessee/me")
	// 체결되지 않은 계약 처리해줘야함
	public ResponseEntity<List<ContractApiDto.Response>> getContractsByLesseeId(@UserInfo Long lesseeId) {
		UserDto lesseeDto = userService.getUserByUserId(lesseeId);
		List<ContractApiDto.Response> response = contractService.getContractByLesseeId(lesseeId).stream()
			.filter(contractDto -> contractDto.getStatus() == ContractStatus.EXECUTION_COMPLETED)
			.map(contractDto -> {
				UserDto lessorDto = userService.getUserByUserId(contractDto.getLessorId());
				RepaymentDto repaymentDto = repaymentService.findTopUnpaidRepaymentByContractId(
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
	public ResponseEntity<List<ContractApiDto.Response>> getContractsByLessorId(@UserInfo Long lessorId) {
		UserDto lessorDto = userService.getUserByUserId(lessorId);
		List<ContractApiDto.Response> response = contractService.getContractByLessorId(lessorId).stream()
			.filter(contractDto -> contractDto.getStatus() == ContractStatus.EXECUTION_COMPLETED)
			.map(contractDto -> {
				UserDto lesseeDto = userService.getUserByUserId(contractDto.getLesseeId());
				RepaymentDto repaymentDto = repaymentService.findTopUnpaidRepaymentByContractId(
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
		if (request.getStatus().equals(ContractStatus.EXECUTION_COMPLETED)) {
			repaymentService.createAllRepayment(contractService.getContractByContractId(contractId));
		}
		return ResponseEntity.ok().build();
	}

	@PatchMapping("/{contractId}")
	public ResponseEntity<ContractApiDto.Response> updateFinalContract(@PathVariable Long contractId,
		@RequestBody ContractApiDto.Request request) throws IOException {
		ContractDto contractDto = contractService.updateFinalContract(contractId, ContractDto.from(request));
		if (request.getStatus().equals(ContractStatus.EXECUTION_COMPLETED)) {
			repaymentService.createAllRepayment(contractService.getContractByContractId(contractId));
		}

		// 전자지갑 업데이트
		// String lesseeAddress = userService.getUserByUserId(contractDto.getLesseeId()).getWalletAddress();
		// String lessorAddress = userService.getUserByUserId(contractDto.getLessorId()).getWalletAddress();

		UserDto lessee = userService.getUserByUserId(contractDto.getLesseeId());
		UserDto lessor = userService.getUserByUserId(contractDto.getLessorId());

		RepaymentDto repaymentDto = repaymentService.findTopUnpaidRepaymentByContractId(contractDto.getContractId());

		if (repaymentDto == null)
			repaymentDto = RepaymentDto.of(0L, 0, null);

		int totalRepaymentCount = repaymentService.getRepaymentsByContractId(contractDto.getContractId())
			.size();

		return ResponseEntity.ok().body(ContractApiDto.Response.of(contractService.getContractByContractId(
				contractDto.getContractId()),
			lessee, lessor, repaymentDto, totalRepaymentCount));

		// 스마트 컨트랙트 생성을 위해서 계약 정보 리턴
		// return ResponseEntity.ok().body(response);
	}
}
