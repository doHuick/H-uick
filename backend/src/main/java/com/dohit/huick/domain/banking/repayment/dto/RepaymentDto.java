package com.dohit.huick.domain.banking.repayment.dto;

import com.dohit.huick.domain.banking.repayment.entity.Repayment;

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
}
