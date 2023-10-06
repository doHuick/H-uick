package com.dohit.huick.domain.contract.dto;

import java.time.LocalDateTime;
import java.util.Objects;

import com.dohit.huick.api.contract.dto.ContractApiDto;
import com.dohit.huick.domain.contract.constant.ContractStatus;
import com.dohit.huick.domain.contract.entity.Contract;
import com.dohit.huick.domain.user.dto.UserDto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ContractDto {
	Long contractId;
	Long lesseeId;
	Long lessorId;
	LocalDateTime startDate;
	LocalDateTime dueDate;
	Long amount;
	String amountInKorean;
	Float rate;
	ContractStatus status;
	LocalDateTime createdTime;
	String pdfPath;
	String useAutoTransfer;

	@Builder
	public ContractDto(Long contractId, Long lesseeId, Long lessorId, LocalDateTime startDate,
		LocalDateTime dueDate, Long amount, String amountInKorean, Float rate, ContractStatus status,
		LocalDateTime createdTime,
		String pdfPath, String useAutoTransfer) {
		this.contractId = contractId;
		this.lesseeId = lesseeId;
		this.lessorId = lessorId;
		this.startDate = startDate;
		this.dueDate = dueDate;
		this.amount = amount;
		this.amountInKorean = amountInKorean;
		this.rate = rate;
		this.status = status;
		this.createdTime = createdTime;
		this.pdfPath = pdfPath;
		this.useAutoTransfer = useAutoTransfer;
	}

	public static ContractDto from(Contract contract) {
		return ContractDto.builder()
			.contractId(contract.getContractId())
			.lesseeId(contract.getLesseeId())
			.lessorId(contract.getLessorId())
			.startDate(contract.getStartDate())
			.dueDate(contract.getDueDate())
			.amount(contract.getAmount())
			.amountInKorean(contract.getAmountInKorean())
			.rate(contract.getRate())
			.status(contract.getStatus())
			.createdTime(contract.getCreatedTime())
			.pdfPath(contract.getPdfPath())
			.useAutoTransfer(contract.getUseAutoTransfer())
			.build();
	}

	public static ContractDto from(ContractApiDto.Request request) {
		return ContractDto.builder()
			.lesseeId(request.getLesseeId())
			.lessorId(request.getLessorId())
			.startDate(request.getStartDate())
			.dueDate(request.getDueDate())
			.amount(request.getAmount())
			.amountInKorean(request.getAmountInKorean())
			.rate(request.getRate())
			.status(request.getStatus())
			.pdfPath(request.getPdfPath())
			.useAutoTransfer(Objects.equals(request.getUseAutoTransfer(), "Y") ? "Y" : "N")
			.build();
	}

	public static ContractDto of(Long contractId, ContractStatus status) {
		return ContractDto.builder()
			.contractId(contractId)
			.status(status)
			.build();
	}
}
