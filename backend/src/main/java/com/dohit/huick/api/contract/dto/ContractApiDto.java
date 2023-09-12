package com.dohit.huick.api.contract.dto;

import java.time.LocalDateTime;

import com.dohit.huick.domain.contract.constant.ContractStatus;
import com.dohit.huick.domain.contract.constant.IntervalUnit;
import com.dohit.huick.domain.contract.dto.ContractDto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Getter;

public class ContractApiDto {

	@Getter
	@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class Request{
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
		String useAutoTransfer;

		@Builder
		private Request(Long lesseeId, Long lessorId, LocalDateTime startDate, LocalDateTime dueDate,
			Integer interval, IntervalUnit intervalUnit,
			Long amount, Long repaymentAmount, Float rate,ContractStatus status, String useAutoTransfer) {
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
			this.useAutoTransfer = useAutoTransfer;
		}
	}

	@Getter
	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class Response {
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

		@Builder
		private Response(Long contractId, Long lesseeId, Long lessorId, LocalDateTime startDate, LocalDateTime dueDate,
			Integer interval, IntervalUnit intervalUnit, Long amount, Long repaymentAmount,Float rate, ContractStatus status, LocalDateTime createdTime) {
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
		}

		public static Response from(ContractDto contractDto) {
			return Response.builder()
				.contractId(contractDto.getContractId())
				.lesseeId(contractDto.getLesseeId())
				.lessorId(contractDto.getLessorId())
				.startDate(contractDto.getStartDate())
				.dueDate(contractDto.getDueDate())
				.interval(contractDto.getInterval())
				.intervalUnit(contractDto.getIntervalUnit())
				.amount(contractDto.getAmount())
				.repaymentAmount(contractDto.getRepaymentAmount())
				.rate(contractDto.getRate())
				.status(contractDto.getStatus())
				.createdTime(contractDto.getCreatedTime())
				.build();
		}
	}
}
