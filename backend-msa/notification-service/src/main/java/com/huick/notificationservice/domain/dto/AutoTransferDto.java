package com.huick.notificationservice.domain.dto;

import java.time.LocalDateTime;
import java.time.Year;
import java.time.temporal.ChronoUnit;

import javax.persistence.Column;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AutoTransferDto {
	private Long autoTransferId;

	@Column(nullable = false)
	private Long contractId;

	@Column(nullable = true)
	private LocalDateTime nextTransferDate;

	@Column(nullable = false)
	private Long amount;

	@Column(nullable = false)
	private Integer unpaidCount;

	@Builder
	public AutoTransferDto(Long autoTransferId, Long contractId, LocalDateTime nextTransferDate, Long amount,
		Integer unpaidCount) {
		this.autoTransferId = autoTransferId;
		this.contractId = contractId;
		this.nextTransferDate = nextTransferDate;
		this.amount = amount;
		this.unpaidCount = unpaidCount;
	}
}
