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

	@Column(nullable = false)
	String amountInKorean;

	@Column(nullable = true)
	Float rate;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	ContractStatus status;

	@CreatedDate
	LocalDateTime createdTime;

	@Column(nullable = true) // 차후 false 로 변경
	String pdfPath;

	@Column(nullable = true) // 차후 false 로 변경
	String useAutoTransfer;

	@Builder
	private Contract(Long contractId, Long lesseeId, Long lessorId, LocalDateTime startDate, LocalDateTime dueDate,
		Long amount, String amountInKorean, Float rate, ContractStatus status, LocalDateTime createdTime, String pdfPath
		, String useAutoTransfer) {
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

	public static Contract from(ContractDto contractDto) {
		return Contract.builder()
			.contractId(contractDto.getContractId())
			.lesseeId(contractDto.getLesseeId())
			.lessorId(contractDto.getLessorId())
			.startDate(contractDto.getStartDate())
			.dueDate(contractDto.getDueDate())
			.amount(contractDto.getAmount())
			.amountInKorean(contractDto.getAmountInKorean())
			.rate(contractDto.getRate())
			.status(contractDto.getStatus())
			.pdfPath(contractDto.getPdfPath())
			.useAutoTransfer(contractDto.getUseAutoTransfer())
			.build();
	}

	public void updateStatus(ContractStatus status) {
		this.status = status;
	}

	public void updatePdfPath(String pdfPath) {
		this.pdfPath = pdfPath;
	}

	public void updateByRequest(ContractDto request) {
		if (this.lesseeId == null && request.getLesseeId() != null) {
			this.lesseeId = request.getLesseeId();
		}
		if (this.lessorId == null && request.getLessorId() != null) {
			this.lessorId = request.getLessorId();
		}
		if (this.startDate == null && request.getStartDate() != null) {
			this.startDate = request.getStartDate();
		}
		if (this.dueDate == null && request.getDueDate() != null) {
			this.dueDate = request.getDueDate();
		}
		if (this.amount == null && request.getAmount() != null) {
			this.amount = request.getAmount();
		}
		if (this.amountInKorean == null && request.getAmountInKorean() != null) {
			this.amountInKorean = request.getAmountInKorean();
		}
		if (this.rate == null && request.getRate() != null) {
			this.rate = request.getRate();
		}
		if(request.getUseAutoTransfer() != null) {
			this.useAutoTransfer = request.getUseAutoTransfer();
		}
	}
}
