package com.dohit.huick.domain.contract.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ContractDto {
	Long contractId;
	Long lesseeId;
	Long lessorId;
	LocalDateTime startDate;
	LocalDateTime dueDate;
	LocalDateTime interval;
	Long amount;
	Float rate;
	String status;
	LocalDateTime createdTime;

	@Builder
	private ContractDto(Long contractId, Long lesseeId, Long lessorId, LocalDateTime startDate, LocalDateTime dueDate,
		LocalDateTime interval, Long amount, Float rate, String status, LocalDateTime createdTime) {
		this.contractId = contractId;
		this.lesseeId = lesseeId;
		this.lessorId = lessorId;
		this.startDate = startDate;
		this.dueDate = dueDate;
		this.interval = interval;
		this.amount = amount;
		this.rate = rate;
		this.status = status;
		this.createdTime = createdTime;
	}
}
