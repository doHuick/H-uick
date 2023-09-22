package com.huick.bankingservice.domain.repayment.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.huick.bankingservice.domain.repayment.dto.RepaymentDto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Repayment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long repaymentId;

	@Column(nullable = false)
	private Long contractId;

	@Column(nullable = false)
	private Long transactionId;

	@Column(nullable = false)
	private Integer repaymentNumber;

	@Builder
	private Repayment(Long repaymentId, Long contractId, Long transactionId, Integer repaymentNumber) {
		this.repaymentId = repaymentId;
		this.contractId = contractId;
		this.transactionId = transactionId;
		this.repaymentNumber = repaymentNumber;
	}

	public static Repayment from (RepaymentDto repaymentDto) {
		return Repayment.builder()
			.repaymentId(repaymentDto.getRepaymentId())
			.contractId(repaymentDto.getContractId())
			.transactionId(repaymentDto.getTransactionId())
			.repaymentNumber(repaymentDto.getRepaymentNumber())
			.build();
	}
}
