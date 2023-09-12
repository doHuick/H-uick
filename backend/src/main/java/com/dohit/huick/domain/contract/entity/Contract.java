package com.dohit.huick.domain.contract.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.dohit.huick.domain.contract.constant.ContractStatus;
import com.dohit.huick.domain.contract.constant.TermUnit;
import com.dohit.huick.domain.contract.dto.ContractDto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Contract {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long contractId;

	@Column(nullable = false)
	Long lesseeId;

	@Column(nullable = false)
	Long lessorId;

	@Column(nullable = false)
	LocalDateTime startDate;

	@Column(nullable = false)
	LocalDateTime dueDate;

	@Column(nullable = true)
	Integer term;

	@Column(nullable = true)
	TermUnit termUnit;

	@Column(nullable = false)
	Long amount;

	@Column(nullable = true)
	Long repaymentAmountPerOnce;

	@Column(nullable = true)
	Float rate;

	@Column(nullable = false)
	ContractStatus status;

	@CreatedDate
	LocalDateTime createdTime;

	@Builder
	private Contract(Long contractId, Long lesseeId, Long lessorId, LocalDateTime startDate, LocalDateTime dueDate,
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

	public static Contract from(ContractDto contractDto) {
		return Contract.builder()
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
			.build();
	}

	public void updateStatus(ContractStatus status) {
		this.status = status;
	}
}
