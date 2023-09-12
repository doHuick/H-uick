package com.dohit.huick.domain.contract.dto;

import java.time.LocalDateTime;

import com.dohit.huick.api.contract.dto.ContractApiDto;
import com.dohit.huick.domain.contract.constant.ContractStatus;
import com.dohit.huick.domain.contract.constant.TermUnit;
import com.dohit.huick.domain.contract.entity.Contract;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ContractDto {
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
	String useAutoTransfer;

	@Builder
	private ContractDto(Long contractId, Long lesseeId, Long lessorId, LocalDateTime startDate, LocalDateTime dueDate,
		Integer term, TermUnit termUnit, Long amount, Long repaymentAmountPerOnce,Float rate, ContractStatus status, LocalDateTime createdTime, String useAutoTransfer) {
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
		this.useAutoTransfer = useAutoTransfer;
	}

	public static ContractDto from(Contract contract) {
		return ContractDto.builder()
			.contractId(contract.getContractId())
			.lesseeId(contract.getLesseeId())
			.lessorId(contract.getLessorId())
			.startDate(contract.getStartDate())
			.dueDate(contract.getDueDate())
			.term(contract.getTerm())
			.termUnit(contract.getTermUnit())
			.amount(contract.getAmount())
			.repaymentAmountPerOnce(contract.getRepaymentAmountPerOnce())
			.rate(contract.getRate())
			.status(contract.getStatus())
			.createdTime(contract.getCreatedTime())
			.build();
	}

	public static ContractDto from(ContractApiDto.Request request) {
		return ContractDto.builder()
			.lesseeId(request.getLesseeId())
			.lessorId(request.getLessorId())
			.startDate(request.getStartDate())
			.dueDate(request.getDueDate())
			.term(request.getTerm())
			.termUnit(request.getTermUnit())
			.amount(request.getAmount())
			.repaymentAmountPerOnce(request.getRepaymentAmountPerOnce())
			.rate(request.getRate())
			.status(request.getStatus())
			.useAutoTransfer(request.getUseAutoTransfer())
			.build();
	}

	public static ContractDto of(Long contractId, ContractStatus status) {
		return ContractDto.builder()
			.contractId(contractId)
			.status(status)
			.build();
	}
}
