package com.dohit.huick.domain.banking.autotransfer.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dohit.huick.domain.banking.autotransfer.dto.AutoTransferDto;
import com.dohit.huick.domain.banking.autotransfer.entity.AutoTransfer;
import com.dohit.huick.domain.banking.autotransfer.repository.AutoTransferRepository;
import com.dohit.huick.global.error.ErrorCode;
import com.dohit.huick.global.error.exception.BankingException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AutoTransferService {
	private final AutoTransferRepository autoTransferRepository;

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	public List<AutoTransferDto> getAutoTransfersOfToday() {
		LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
		LocalDateTime endOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
		return autoTransferRepository.findByNextTransferDateBetween(startOfDay, endOfDay).stream().map(
			AutoTransferDto::from).collect(
			Collectors.toList());
	}

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	public void createAutoTransfer(AutoTransferDto autoTransferDto) {
		autoTransferRepository.save(AutoTransfer.from(autoTransferDto));
	}

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	public void increaseUnpaidCount(Long autoTransferId) {
		AutoTransfer autoTransfer = autoTransferRepository.findByAutoTransferId(autoTransferId).orElseThrow(() -> new BankingException(
			ErrorCode.NOT_EXIST_AUTO_TRANSFER));
		autoTransfer.updateUnpaidCount(autoTransfer.getUnpaidCount() + 1);
	}

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	public void decreaseUnpaidCount(Long autoTransferId) {
		AutoTransfer autoTransfer = autoTransferRepository.findByAutoTransferId(autoTransferId).orElseThrow(() -> new BankingException(
			ErrorCode.NOT_EXIST_AUTO_TRANSFER));
		autoTransfer.updateUnpaidCount(autoTransfer.getUnpaidCount() - 1);
	}

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	public void updateNextTransferDate(Long autoTransferId, LocalDateTime nextTransferDate) {
		AutoTransfer autoTransfer = autoTransferRepository.findByAutoTransferId(autoTransferId).orElseThrow(() -> new BankingException(ErrorCode.NOT_EXIST_AUTO_TRANSFER));
		autoTransfer.updateNextTransferDate(nextTransferDate);
	}

	public List<AutoTransferDto> getAutoTransfersAfter3Days() {
		LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now().plusDays(3L), LocalTime.MIN);
		LocalDateTime endOfDay = LocalDateTime.of(LocalDate.now().plusDays(3L), LocalTime.MAX);
		return autoTransferRepository.findByNextTransferDateBetween(startOfDay, endOfDay).stream().map(
			AutoTransferDto::from).collect(
			Collectors.toList());
	}
}
