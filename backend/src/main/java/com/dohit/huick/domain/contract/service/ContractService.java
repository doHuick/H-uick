package com.dohit.huick.domain.contract.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dohit.huick.domain.banking.autotransfer.dto.AutoTransferDto;
import com.dohit.huick.domain.banking.autotransfer.service.AutoTransferService;
import com.dohit.huick.domain.contract.constant.ContractStatus;
import com.dohit.huick.domain.contract.dto.ContractDto;
import com.dohit.huick.domain.contract.entity.Contract;
import com.dohit.huick.domain.contract.repository.ContractRepository;
import com.dohit.huick.global.error.ErrorCode;
import com.dohit.huick.global.error.exception.ContractException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ContractService {
	private final ContractRepository contractRepository;
	private final AutoTransferService autoTransferService;

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	public void createContract(ContractDto contractDto) {
		// 계약 생성 메서드
		Contract contract = contractRepository.save(Contract.from(contractDto));
		if (contractDto.getUseAutoTransfer().equals("Y")) {
			autoTransferService.createAutoTransfer(AutoTransferDto.from(contractDto));
		}
	}

	public ContractDto getContractByContractId(Long contractId) {
		return ContractDto.from(contractRepository.findByContractId(contractId).orElseThrow(() -> new ContractException(
			ErrorCode.NOT_EXIST_CONTRACT)));
	}

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	public void updateContractStatus(Long contractId, ContractStatus contractStatus) {
		Contract contract = contractRepository.findByContractId(contractId).orElseThrow(() -> new ContractException(
			ErrorCode.NOT_EXIST_CONTRACT));
		contract.updateStatus(contractStatus);
	}

	public List<ContractDto> getContractByLesseeId(Long lesseeId) {
		return contractRepository.findByLesseeId(lesseeId).stream().map(ContractDto::from).collect(Collectors.toList());
	}

	public List<ContractDto> getContractByLessorId(Long lessorId) {
		return contractRepository.findByLessorId(lessorId).stream().map(ContractDto::from).collect(Collectors.toList());
	}
}