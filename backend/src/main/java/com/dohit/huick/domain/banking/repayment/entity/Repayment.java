package com.dohit.huick.domain.banking.repayment.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.dohit.huick.domain.banking.repayment.constant.RepaymentStatus;

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

	@Column(nullable = true)
	private Long transactionId;

	@Column(nullable = false)
	private Long amount;

	@Column(nullable = false)
	private Long balance;

	@Column(nullable = false)
	private LocalDateTime repaymentDate;

	@Column(nullable = false)
	private Integer repaymentCount;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private RepaymentStatus status;

	@Builder
	private Repayment(Long repaymentId, Long contractId, Long transactionId, Long amount, Long balance,
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

	public static Repayment of(Long contractId, Long amount, LocalDateTime repaymentTime, Integer repaymentCount,
		String useAutoTransfer) {
		return Repayment.builder()
			.contractId(contractId)
			.amount(amount)
			.balance(amount)
			.repaymentDate(repaymentTime)
			.repaymentCount(repaymentCount)
			.status(RepaymentStatus.UNPAID)
			.build();
	}

	public void updateStatusPAIDAndTransactionId(Long transactionId) {
		this.status = RepaymentStatus.PAID;
		this.transactionId = transactionId;
	}
}