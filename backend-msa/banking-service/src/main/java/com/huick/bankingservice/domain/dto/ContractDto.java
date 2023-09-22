package com.huick.bankingservice.domain.dto;

import java.time.LocalDateTime;

import com.huick.bankingservice.domain.constant.ContractStatus;

import com.huick.bankingservice.feign.contract.dto.ContractApiDto;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

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
	private ContractDto(Long contractId, Long lesseeId, Long lessorId, LocalDateTime startDate,
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

    public static ContractDto from(ContractApiDto.Response response) {
		return ContractDto.builder()
			.contractId(response.getContractId())
				.lesseeId(response.getLesseeId())
				.lessorId(response.getLessorId())
				.startDate(response.getStartDate())
				.dueDate(response.getDueDate())
				.amount(response.getAmount())
				.rate(response.getRate())
				.status(response.getStatus())
				.createdTime(response.getCreatedTime())
				.pdfPath(response.getPdfPath())
				.build();
    }
}


