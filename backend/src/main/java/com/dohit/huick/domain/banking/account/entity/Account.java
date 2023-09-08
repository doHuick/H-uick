package com.dohit.huick.domain.banking.account.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long accountId;

	@Column(nullable = false, unique = true)
	private String accountNumber;

	@Column(nullable = false)
	private Long userId;

	@Column(nullable = false)
	private String bankCode;

	@Column(nullable = false)
	private Long balance;

	@CreatedDate
	private LocalDateTime createdTime;

	@Builder
	private Account(Long accountId, String accountNumber, Long userId, String bankCode, Long balance,
		LocalDateTime createdTime) {
		this.accountId = accountId;
		this.accountNumber = accountNumber;
		this.userId = userId;
		this.bankCode = bankCode;
		this.balance = balance;
		this.createdTime = createdTime;
	}

	public static Account of(String accountNumber, Long userId, String bankCode, Long balance) {
		return Account.builder()
			.accountNumber(accountNumber)
			.userId(userId)
			.bankCode(bankCode)
			.balance(balance)
			.build();
	}

	public void updateBalance(Long balance) {
		this.balance = balance;
	}
}
