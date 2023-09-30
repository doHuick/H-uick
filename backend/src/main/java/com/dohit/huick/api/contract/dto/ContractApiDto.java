package com.dohit.huick.api.contract.dto;

import java.time.LocalDateTime;

import com.dohit.huick.domain.banking.repayment.dto.RepaymentDto;
import com.dohit.huick.domain.contract.constant.ContractStatus;
import com.dohit.huick.domain.contract.dto.ContractDto;
import com.dohit.huick.domain.user.dto.UserDto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ContractApiDto {

	@Getter
	@NoArgsConstructor
	@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class Request {
		Long lesseeId;
		Long lessorId;
		LocalDateTime startDate;
		LocalDateTime dueDate;
		Long amount;
		Float rate;
		ContractStatus status;
		String pdfPath;
		String useAutoTransfer;

		@Builder
		private Request(Long lesseeId, Long lessorId, LocalDateTime startDate, LocalDateTime dueDate, Long amount,
			Float rate, ContractStatus status, String pdfPath, String useAutoTransfer) {
			this.lesseeId = lesseeId;
			this.lessorId = lessorId;
			this.startDate = startDate;
			this.dueDate = dueDate;
			this.amount = amount;
			this.rate = rate;
			this.status = status;
			this.pdfPath = pdfPath;
			this.useAutoTransfer = useAutoTransfer;
		}
	}

	@Getter
	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class Response {
		Long contractId;
		Long lesseeId;
		Long lessorId;
		String lesseeName;
		String lesseeAddress;
		String lessorName;
		String lessorAddress;
		String lessorRrn;
		String lessorPhoneNumber;
		Integer totalRepaymentCount;
		Integer currentRepaymentCount;
		LocalDateTime startDate;
		LocalDateTime dueDate;
		LocalDateTime repaymentDate;
		Long currentAmount;
		Long amount;
		Float rate;
		ContractStatus status;
		String pdfPath;

		@Builder
		private Response(Long contractId, Long lesseeId, Long lessorId, String lesseeName, String lesseeAddress,
			String lessorName, String lessorAddress, String lessorRrn, String lessorPhoneNumber,
			Integer totalRepaymentCount, Integer currentRepaymentCount, LocalDateTime startDate,
			LocalDateTime dueDate, LocalDateTime repaymentDate, Long currentAmount, Long amount, Float rate,
			ContractStatus status, String pdfPath) {
			this.contractId = contractId;
			this.lesseeId = lesseeId;
			this.lessorId = lessorId;
			this.lesseeName = lesseeName;
			this.lesseeAddress = lesseeAddress;
			this.lessorName = lessorName;
			this.lessorAddress = lessorAddress;
			this.lessorRrn = lessorRrn;
			this.lessorPhoneNumber = lessorPhoneNumber;
			this.totalRepaymentCount = totalRepaymentCount;
			this.currentRepaymentCount = currentRepaymentCount;
			this.startDate = startDate;
			this.dueDate = dueDate;
			this.repaymentDate = repaymentDate;
			this.currentAmount = currentAmount;
			this.amount = amount;
			this.rate = rate;
			this.status = status;
			this.pdfPath = pdfPath;
		}

		public static Response of(ContractDto contractDto, UserDto lesseeDto, UserDto lessorDto,
			RepaymentDto repaymentDto, int totalRepaymentCount) {
			return Response.builder()
				.contractId(contractDto.getContractId())
				.lesseeId(contractDto.getLesseeId())
				.lessorId(contractDto.getLessorId())
				.lesseeName(lesseeDto.getName())
				.lesseeAddress(lesseeDto.getAddress())
				.lessorName(lessorDto.getName())
				.lessorAddress(lessorDto.getAddress())
				.lessorRrn(lessorDto.getRrn())
				.lessorPhoneNumber(lessorDto.getPhoneNumber())
				.totalRepaymentCount(totalRepaymentCount)
				.currentRepaymentCount(repaymentDto.getRepaymentCount())
				.startDate(contractDto.getStartDate())
				.dueDate(contractDto.getDueDate())
				.repaymentDate(repaymentDto.getRepaymentDate())
				.currentAmount(repaymentDto.getAmount())
				.amount(contractDto.getAmount())
				.rate(contractDto.getRate())
				.status(contractDto.getStatus())
				.pdfPath(contractDto.getPdfPath())
				.build();
		}

		public static Response from(ContractDto contractDto) {
			return Response.builder()
				.contractId(contractDto.getContractId())
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
	}
}