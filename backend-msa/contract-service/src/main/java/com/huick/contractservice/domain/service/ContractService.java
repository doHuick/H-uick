package com.huick.contractservice.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huick.contractservice.domain.constant.ContractStatus;
import com.huick.contractservice.domain.dto.AutoTransferDto;
import com.huick.contractservice.domain.dto.ContractDto;
import com.huick.contractservice.domain.dto.TransactionDto;
import com.huick.contractservice.domain.entity.Contract;
import com.huick.contractservice.domain.repository.ContractRepository;
import com.huick.contractservice.feign.banking.client.BankingServiceClient;
import com.huick.contractservice.global.error.ErrorCode;
import com.huick.contractservice.global.error.exception.ContractException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ContractService {
	private final ContractRepository contractRepository;
	private final KafkaProducer kafkaProducer;
	private final BankingServiceClient bankingServiceClient;
	// private final AutoTransferService autoTransferService;	// 카프카 사용

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	public void createContract(ContractDto contractDto) {
		// 계약 생성 메서드
		Contract contract = contractRepository.save(Contract.from(contractDto));
		if (contractDto.getUseAutoTransfer().equals("Y")) {
			// autoTransferService.createAutoTransfer(AutoTransferDto.from(contractDto));
			// 카프카 사용하여 데이터 생성
			kafkaProducer.createAutoTransfer("create-autotransfer-topic", AutoTransferDto.from(contractDto));
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

		if(contractStatus == ContractStatus.EXECUTION_COMPLETED) {
			// 송금 시도를 하고
			String sender = bankingServiceClient.getAccountByUserId(contract.getLessorId()).getAccountNumber();
			String receiver = bankingServiceClient.getAccountByUserId(contract.getLessorId()).getAccountNumber();
			kafkaProducer.transferMoney("transfer-money", TransactionDto.of(sender, receiver, contract.getAmount()));
			contract.updateStatus(contractStatus);
		} else {
			contract.updateStatus(contractStatus);
		}
	}

	public List<ContractDto> getContractByLesseeId(Long lesseeId) {
		return contractRepository.findByLesseeId(lesseeId).stream().map(ContractDto::from).collect(Collectors.toList());
	}

	public List<ContractDto> getContractByLessorId(Long lessorId) {
		return contractRepository.findByLessorId(lessorId).stream().map(ContractDto::from).collect(Collectors.toList());
	}
}
