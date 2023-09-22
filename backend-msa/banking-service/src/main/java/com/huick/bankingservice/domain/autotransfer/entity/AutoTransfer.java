package com.huick.bankingservice.domain.autotransfer.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.huick.bankingservice.domain.autotransfer.dto.AutoTransferDto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AutoTransfer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long autoTransferId;

	@Column(nullable = false)
	private Long contractId;

	@Column(nullable = true)
	private LocalDateTime nextTransferDate;

	@Column(nullable = false)
	private Long amount;

	@Column(nullable = true)
	private Integer unpaidCount;

	@Builder
	public AutoTransfer(Long autoTransferId, Long contractId, LocalDateTime nextTransferDate, Long amount,
		Integer unpaidCount) {
		this.autoTransferId = autoTransferId;
		this.contractId = contractId;
		this.nextTransferDate = nextTransferDate;
		this.amount = amount;
		this.unpaidCount = unpaidCount;
	}

	public static AutoTransfer from(AutoTransferDto autoTransferDto) {
		return AutoTransfer.builder()
			.contractId(autoTransferDto.getContractId())
			.nextTransferDate(autoTransferDto.getNextTransferDate())
			.amount(autoTransferDto.getAmount())
			.unpaidCount(autoTransferDto.getUnpaidCount())
			.build();
	}

	public void updateUnpaidCount(Integer unpaidCount) {
		this.unpaidCount = unpaidCount;
	}

	public void updateNextTransfer(LocalDateTime nextTransferDate, Long amount) {
		this.nextTransferDate = nextTransferDate;
		this.amount = amount;
	}
}
