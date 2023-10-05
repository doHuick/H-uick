package com.dohit.huick.domain.banking.repayment.dto;

import java.time.LocalDateTime;

import com.dohit.huick.domain.banking.repayment.constant.RepaymentStatus;
import com.dohit.huick.domain.banking.repayment.entity.Repayment;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RepaymentDto {
	private Long repaymentId;
	private Long contractId;
	private Long transactionId;
	private Long amount;
	private Long balance;
	private LocalDateTime repaymentDate;
	private Integer repaymentCount;
	private RepaymentStatus status;

	@Builder
	private RepaymentDto(Long repaymentId, Long contractId, Long transactionId, Long amount, Long balance,
		LocalDateTime repaymentDate, Integer repaymentCount,
		RepaymentStatus status) {
		this.repaymentId = repaymentId;
		this.contractId = contractId;
		this.transactionId = transactionId;
		this.amount = amount;
		this.balance = balance;
		this.repaymentDate = repaymentDate;
		this.repaymentCount = repaymentCount;
		this.status = status;
	}

	// public static RepaymentDto of(Long contractId, Long transactionId, Integer repaymentNumber) {
	// 	return RepaymentDto.builder()
	// 		.contractId(contractId)
	// 		.transactionId(transactionId)
	// 		.repaymentNumber(repaymentNumber)
	// 		.build();
	// }

	public static RepaymentDto from(Repayment repayment) {
		return RepaymentDto.builder()
			.repaymentId(repayment.getRepaymentId())
			.contractId(repayment.getContractId())
			.transactionId(repayment.getTransactionId())
			.amount(repayment.getAmount())
			.balance(repayment.getBalance())
			.repaymentDate(repayment.getRepaymentDate())
			.repaymentCount(repayment.getRepaymentCount())
			.status(repayment.getStatus())
			.build();
	}

	public static RepaymentDto of(Long amount, Integer repaymentCount, LocalDateTime repaymentDate) {
		return RepaymentDto.builder()
			.amount(amount)
			.repaymentCount(repaymentCount)
			.repaymentDate(repaymentDate)
			.build();
	}
}
