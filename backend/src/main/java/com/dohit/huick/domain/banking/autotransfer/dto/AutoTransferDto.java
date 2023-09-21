package com.dohit.huick.domain.banking.autotransfer.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;

import com.dohit.huick.domain.banking.autotransfer.entity.AutoTransfer;

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

	public static AutoTransferDto from(AutoTransfer autoTransfer) {
		return AutoTransferDto.builder()
			.autoTransferId(autoTransfer.getAutoTransferId())
			.contractId(autoTransfer.getContractId())
			.nextTransferDate(autoTransfer.getNextTransferDate())
			.amount(autoTransfer.getAmount())
			.unpaidCount(autoTransfer.getUnpaidCount())
			.build();
	}

	public static AutoTransferDto of(Long contractId, Integer unpaidCount) {
		return AutoTransferDto.builder()
			.contractId(contractId)
			.unpaidCount(unpaidCount)
			.build();
	}
}
