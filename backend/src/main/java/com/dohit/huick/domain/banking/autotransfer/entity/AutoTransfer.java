package com.dohit.huick.domain.banking.autotransfer.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.dohit.huick.domain.banking.autotransfer.dto.AutoTransferDto;

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

	@Column(nullable = true)
	private Integer unpaidCount;

	@Builder
	private AutoTransfer(Long autoTransferId, Long contractId, LocalDateTime nextTransferDate, Integer unpaidCount) {
		this.autoTransferId = autoTransferId;
		this.contractId = contractId;
		this.nextTransferDate = nextTransferDate;
		this.unpaidCount = unpaidCount;
	}
}
