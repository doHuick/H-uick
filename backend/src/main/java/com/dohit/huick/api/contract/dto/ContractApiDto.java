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
		String amountInKorean;
		Float rate;
		ContractStatus status;
		String pdfPath;
		String useAutoTransfer;

		@Builder
		private Request(Long lesseeId, Long lessorId, LocalDateTime startDate, LocalDateTime dueDate, Long amount,
			String amountInKorean,
			Float rate, ContractStatus status, String pdfPath, String useAutoTransfer) {
			this.lesseeId = lesseeId;
			this.lessorId = lessorId;
			this.startDate = startDate;
			this.dueDate = dueDate;
			this.amount = amount;
			this.amountInKorean = amountInKorean;
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
		String lesseeRrn;
		String lesseePhoneNumber;
		String lesseeWalletAddress;
		String lessorName;
		String lessorAddress;
		String lessorRrn;
		String lessorPhoneNumber;
		String lessorWalletAddress;
		Integer totalRepaymentCount;
		Integer currentRepaymentCount;
		Integer paidCount;
		LocalDateTime startDate;
		LocalDateTime dueDate;
		LocalDateTime repaymentDate;
		Long currentAmount;
		Long amount;
		Long balance;
		String amountInKorean;
		Float rate;
		ContractStatus status;
		String pdfPath;
		String useAutoTransfer;

		@Builder
		private Response(Long contractId, Long lesseeId, Long lessorId, String lesseeName, String lesseeAddress,
			String lesseeRrn, String lesseePhoneNumber, String lesseeWalletAddress, String lessorName,
			String lessorAddress, String lessorRrn, String lessorPhoneNumber, String lessorWalletAddress,
			Integer totalRepaymentCount, Integer currentRepaymentCount, Integer paidCount, LocalDateTime startDate,
			LocalDateTime dueDate, LocalDateTime repaymentDate, Long currentAmount, Long amount, Long balance,
			String amountInKorean, Float rate, ContractStatus status, String pdfPath, String useAutoTransfer) {
			this.contractId = contractId;
			this.lesseeId = lesseeId;
			this.lessorId = lessorId;
			this.lesseeName = lesseeName;
			this.lesseeAddress = lesseeAddress;
			this.lesseeRrn = lesseeRrn;
			this.lesseePhoneNumber = lesseePhoneNumber;
			this.lesseeWalletAddress = lesseeWalletAddress;
			this.lessorName = lessorName;
			this.lessorAddress = lessorAddress;
			this.lessorRrn = lessorRrn;
			this.lessorPhoneNumber = lessorPhoneNumber;
			this.lessorWalletAddress = lessorWalletAddress;
			this.totalRepaymentCount = totalRepaymentCount;
			this.currentRepaymentCount = currentRepaymentCount;
			this.paidCount = paidCount;
			this.startDate = startDate;
			this.dueDate = dueDate;
			this.repaymentDate = repaymentDate;
			this.currentAmount = currentAmount;
			this.amount = amount;
			this.balance = balance;
			this.amountInKorean = amountInKorean;
			this.rate = rate;
			this.status = status;
			this.pdfPath = pdfPath;
			this.useAutoTransfer = useAutoTransfer;
		}

		public static Response of(ContractDto contractDto, UserDto lesseeDto, UserDto lessorDto,
			RepaymentDto repaymentDto, int totalRepaymentCount) {
			return Response.builder()
				.contractId(contractDto.getContractId())
				.lesseeId(contractDto.getLesseeId())
				.lessorId(contractDto.getLessorId())
				.lesseeName(lesseeDto.getName())
				.lesseeAddress(lesseeDto.getAddress())
				.lesseeRrn(lesseeDto.getRrn())
				.lesseePhoneNumber(lesseeDto.getPhoneNumber())
				.lesseeWalletAddress(lesseeDto.getWalletAddress())
				.lessorName(lessorDto.getName())
				.lessorAddress(lessorDto.getAddress())
				.lessorRrn(lessorDto.getRrn())
				.lessorPhoneNumber(lessorDto.getPhoneNumber())
				.lessorWalletAddress(lessorDto.getWalletAddress())
				.totalRepaymentCount(totalRepaymentCount)
				.currentRepaymentCount(repaymentDto.getRepaymentCount())
				.startDate(contractDto.getStartDate())
				.dueDate(contractDto.getDueDate())
				.repaymentDate(repaymentDto.getRepaymentDate())
				.currentAmount(repaymentDto.getAmount())
				.amount(contractDto.getAmount())
				.amountInKorean(contractDto.getAmountInKorean())
				.rate(contractDto.getRate())
				.status(contractDto.getStatus())
				.pdfPath(contractDto.getPdfPath())
				.useAutoTransfer(contractDto.getUseAutoTransfer())
				.build();
		}

		public static Response of(ContractDto contractDto, UserDto lesseeDto, UserDto lessorDto,
			RepaymentDto repaymentDto, int totalRepaymentCount, int paidCount, Long balance) {
			return Response.builder()
				.contractId(contractDto.getContractId())
				.lesseeId(contractDto.getLesseeId())
				.lessorId(contractDto.getLessorId())
				.lesseeName(lesseeDto.getName())
				.lesseeAddress(lesseeDto.getAddress())
				.lesseeRrn(lesseeDto.getRrn())
				.lesseePhoneNumber(lesseeDto.getPhoneNumber())
				.lesseeWalletAddress(lesseeDto.getWalletAddress())
				.lessorName(lessorDto.getName())
				.lessorAddress(lessorDto.getAddress())
				.lessorRrn(lessorDto.getRrn())
				.lessorPhoneNumber(lessorDto.getPhoneNumber())
				.lessorWalletAddress(lessorDto.getWalletAddress())
				.totalRepaymentCount(totalRepaymentCount)
				.currentRepaymentCount(repaymentDto.getRepaymentCount())
				.paidCount(paidCount)
				.startDate(contractDto.getStartDate())
				.dueDate(contractDto.getDueDate())
				.repaymentDate(repaymentDto.getRepaymentDate())
				.currentAmount(repaymentDto.getAmount())
				.amount(contractDto.getAmount())
				.balance(balance)
				.amountInKorean(contractDto.getAmountInKorean())
				.rate(contractDto.getRate())
				.status(contractDto.getStatus())
				.pdfPath(contractDto.getPdfPath())
				.useAutoTransfer(contractDto.getUseAutoTransfer())
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
				.amountInKorean(contractDto.getAmountInKorean())
				.rate(contractDto.getRate())
				.status(contractDto.getStatus())
				.pdfPath(contractDto.getPdfPath())
				.useAutoTransfer(contractDto.getUseAutoTransfer())
				.build();
		}

		public static Response updateWalletAddress(String lesseeWalletAddress, String lessorWalletAddress) {
			return Response.builder()
				.lesseeWalletAddress(lesseeWalletAddress)
				.lessorWalletAddress(lessorWalletAddress)
				.build();
		}
	}
}