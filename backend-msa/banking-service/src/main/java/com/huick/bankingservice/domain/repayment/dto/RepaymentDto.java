package com.huick.bankingservice.domain.repayment.dto;

import com.huick.bankingservice.domain.repayment.entity.Repayment;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RepaymentDto {
	private Long repaymentId;
	private Long contractId;
	private Long transactionId;
	private Integer repaymentNumber;

	@Builder
	private RepaymentDto(Long repaymentId, Long contractId, Long transactionId, Integer repaymentNumber) {
		this.repaymentId = repaymentId;
		this.contractId = contractId;
		this.transactionId = transactionId;
		this.repaymentNumber = repaymentNumber;
	}

	public static RepaymentDto of(Long contractId, Long transactionId, Integer repaymentNumber) {
		return RepaymentDto.builder()
			.contractId(contractId)
			.transactionId(transactionId)
			.repaymentNumber(repaymentNumber)
			.build();
	}

	public static RepaymentDto from(Repayment repayment) {
		return RepaymentDto.builder()
			.repaymentId(repayment.getRepaymentId())
			.contractId(repayment.getContractId())
			.transactionId(repayment.getTransactionId())
			.repaymentNumber(repayment.getRepaymentNumber())
			.build();
	}
}
