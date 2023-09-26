package com.dohit.huick.domain.contract.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.dohit.huick.domain.contract.constant.ContractStatus;
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

	@Column(nullable = true)
	Long lesseeId;

	@Column(nullable = true)
	Long lessorId;

	@Column(nullable = false)
	LocalDateTime startDate;

	@Column(nullable = false)
	LocalDateTime dueDate;

	@Column(nullable = false)
	Long amount;

	@Column(nullable = true)
	Float rate;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	ContractStatus status;

	@CreatedDate
	LocalDateTime createdTime;

	@Column(nullable = true) // 차후 false 로 변경
	String pdfPath;

	@Builder
	private Contract(Long contractId, Long lesseeId, Long lessorId, LocalDateTime startDate, LocalDateTime dueDate,
		Long amount, Float rate, ContractStatus status, LocalDateTime createdTime, String pdfPath) {
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
	}

	public static Contract from(ContractDto contractDto) {
		return Contract.builder()
			.lesseeId(contractDto.getLesseeId())
			.lessorId(contractDto.getLessorId())
			.startDate(contractDto.getStartDate())
			.dueDate(contractDto.getDueDate())
			.amount(contractDto.getAmount())
			.rate(contractDto.getRate())
			.status(contractDto.getStatus())
			.pdfPath(contractDto.getPdfPath())
			.build();
	}

	public void updateStatus(ContractStatus status) {
		this.status = status;
	}
}
