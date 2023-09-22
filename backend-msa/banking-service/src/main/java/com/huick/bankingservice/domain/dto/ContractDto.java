package com.huick.bankingservice.domain.dto;

import java.time.LocalDateTime;

import com.huick.bankingservice.domain.constant.ContractStatus;

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
	Float rate;
	ContractStatus status;
	LocalDateTime createdTime;
	String pdfPath;
	String useAutoTransfer;

	@Builder
	public ContractDto(Long contractId, Long lesseeId, Long lessorId, LocalDateTime startDate,
		LocalDateTime dueDate, Long amount, Float rate, ContractStatus status, LocalDateTime createdTime,
		String pdfPath, String useAutoTransfer) {
		this.contractId = contractId;
		this.lesseeId = lesseeId;
		this.lessorId = lessorId;
		this.startDate = startDate;
		this.dueDate = dueDate;
		this.amount = amount;
		this.rate = rate;
		this.status = status;
		this.createdTime = createdTime;
		this.pdfPath = pdfPath;
		this.useAutoTransfer = useAutoTransfer;
	}
	public static ContractDto of(Long contractId, ContractStatus status) {
		return ContractDto.builder()
			.contractId(contractId)
			.status(status)
			.build();
	}
}


