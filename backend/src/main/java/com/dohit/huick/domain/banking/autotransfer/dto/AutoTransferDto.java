package com.dohit.huick.domain.banking.autotransfer.dto;

import java.time.LocalDateTime;

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
	private Integer unpaidCount;

	@Builder
	private AutoTransferDto(Long autoTransferId, Long contractId, LocalDateTime nextTransferDate, Integer unpaidCount) {
		this.autoTransferId = autoTransferId;
		this.contractId = contractId;
		this.nextTransferDate = nextTransferDate;
		this.unpaidCount = unpaidCount;
	}

	public static AutoTransferDto of(Long contractId, Integer unpaidCount) {
		return AutoTransferDto.builder()
			.contractId(contractId)
			.unpaidCount(unpaidCount)
			.build();
	}
}
