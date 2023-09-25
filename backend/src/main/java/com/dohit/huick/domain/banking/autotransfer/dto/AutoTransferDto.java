package com.dohit.huick.domain.banking.autotransfer.dto;

import java.time.LocalDateTime;
import java.time.Year;
import java.time.temporal.ChronoUnit;

import javax.persistence.Column;

import com.dohit.huick.domain.banking.autotransfer.entity.AutoTransfer;
import com.dohit.huick.domain.contract.dto.ContractDto;

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

	public static AutoTransferDto from(ContractDto contractDto) {
		boolean lessThanMonth = contractDto.getStartDate().plusMonths(1L).isAfter(contractDto.getDueDate());
		LocalDateTime nextTransferDate =
			lessThanMonth ? contractDto.getDueDate() : contractDto.getStartDate().plusMonths(1L);
		long between = ChronoUnit.DAYS.between(contractDto.getStartDate(), nextTransferDate);

		boolean isStartDateLeapYear = Year.of(contractDto.getStartDate().getYear()).isLeap();
		boolean isNextTransferDateLeapYear = Year.of(nextTransferDate.getYear()).isLeap();
		long amount = (long)(contractDto.getAmount() * (1 + contractDto.getRate()) / (isStartDateLeapYear ? 366 : 365)
			* between);
		if (contractDto.getStartDate().getYear() != nextTransferDate.getYear()) {
			int nextTransferDateDayOfYear = nextTransferDate.getDayOfYear();
			amount = (long)(
				contractDto.getAmount() * (1 + contractDto.getRate()) / (isStartDateLeapYear ? 366 : 365) * (between
					- nextTransferDateDayOfYear)
					+ contractDto.getAmount() * (1 + contractDto.getRate()) / (isNextTransferDateLeapYear ? 366 : 365)
					* nextTransferDateDayOfYear);
		}

		return AutoTransferDto.builder()
			.contractId(contractDto.getContractId())
			.nextTransferDate(nextTransferDate)
			.amount(
				(long)(contractDto.getAmount() * (1 + contractDto.getRate()) / 365 * (
					ChronoUnit.DAYS.between(nextTransferDate, contractDto.getStartDate()))))
			.amount(amount)
			.unpaidCount(0)
			.build();
	}
}
