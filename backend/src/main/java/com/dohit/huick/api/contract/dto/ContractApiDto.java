package com.dohit.huick.api.contract.dto;

import java.time.LocalDateTime;

import com.dohit.huick.domain.contract.constant.ContractStatus;
import com.dohit.huick.domain.contract.constant.TermUnit;
import com.dohit.huick.domain.contract.dto.ContractDto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ContractApiDto {

	@Getter
	@NoArgsConstructor
	@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class Request{
		Long lesseeId;
		Long lessorId;
		LocalDateTime startDate;
		LocalDateTime dueDate;
		Integer term;
		TermUnit termUnit;
		Long amount;
		Long repaymentAmountPerOnce;
		Float rate;
		ContractStatus status;
		String useAutoTransfer;

		@Builder
		private Request(Long lesseeId, Long lessorId, LocalDateTime startDate, LocalDateTime dueDate,
			Integer term, TermUnit termUnit,
			Long amount, Long repaymentAmountPerOnce, Float rate,ContractStatus status, String useAutoTransfer) {
			this.lesseeId = lesseeId;
			this.lessorId = lessorId;
			this.startDate = startDate;
			this.dueDate = dueDate;
			this.term = term;
			this.termUnit = termUnit;
			this.amount = amount;
			this.repaymentAmountPerOnce = repaymentAmountPerOnce;
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
		Integer term;
		TermUnit termUnit;
		Long amount;
		Long repaymentAmountPerOnce;
		Float rate;
		ContractStatus status;
		LocalDateTime createdTime;

		@Builder
		private Response(Long contractId, Long lesseeId, Long lessorId, LocalDateTime startDate, LocalDateTime dueDate,
			Integer term, TermUnit termUnit, Long amount, Long repaymentAmountPerOnce,Float rate, ContractStatus status, LocalDateTime createdTime) {
			this.contractId = contractId;
			this.lesseeId = lesseeId;
			this.lessorId = lessorId;
			this.startDate = startDate;
			this.dueDate = dueDate;
			this.term = term;
			this.termUnit = termUnit;
			this.amount = amount;
			this.repaymentAmountPerOnce = repaymentAmountPerOnce;
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
				.term(contractDto.getTerm())
				.termUnit(contractDto.getTermUnit())
				.amount(contractDto.getAmount())
				.repaymentAmountPerOnce(contractDto.getRepaymentAmountPerOnce())
				.rate(contractDto.getRate())
				.status(contractDto.getStatus())
				.createdTime(contractDto.getCreatedTime())
				.build();
		}
	}
}
