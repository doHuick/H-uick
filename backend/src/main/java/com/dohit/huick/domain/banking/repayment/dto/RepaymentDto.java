package com.dohit.huick.domain.banking.repayment.dto;

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
}
