package com.dohit.huick.domain.contract.dto;

import java.time.LocalDateTime;

import com.dohit.huick.domain.contract.constant.ContractStatus;
import com.dohit.huick.domain.contract.constant.IntervalUnit;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ContractDto {
	Long contractId;
	Long lesseeId;
	Long lessorId;
	LocalDateTime startDate;
	LocalDateTime dueDate;
	Integer interval;
	IntervalUnit intervalUnit;
	Long amount;
	Long repaymentAmount;
	Float rate;
	ContractStatus status;
	LocalDateTime createdTime;
	String useAutoTransfer;

	@Builder
	private ContractDto(Long contractId, Long lesseeId, Long lessorId, LocalDateTime startDate, LocalDateTime dueDate,
		Integer interval, IntervalUnit intervalUnit, Long amount, Long repaymentAmount,Float rate, ContractStatus status, LocalDateTime createdTime, String useAutoTransfer) {
		this.contractId = contractId;
		this.lesseeId = lesseeId;
		this.lessorId = lessorId;
		this.startDate = startDate;
		this.dueDate = dueDate;
		this.interval = interval;
		this.intervalUnit = intervalUnit;
		this.amount = amount;
		this.repaymentAmount = repaymentAmount;
		this.rate = rate;
		this.status = status;
		this.createdTime = createdTime;
		this.useAutoTransfer = useAutoTransfer;
	}

	public static ContractDto of(Long contractId, ContractStatus status) {
		return ContractDto.builder()
			.contractId(contractId)
			.status(status)
			.build();
	}
}
