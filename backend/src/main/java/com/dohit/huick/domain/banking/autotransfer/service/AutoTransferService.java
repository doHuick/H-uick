package com.dohit.huick.domain.banking.autotransfer.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

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

	public List<AutoTransferDto> getAutoTransfersOfToday() {
		LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
		LocalDateTime endOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
		return autoTransferRepository.findByNextTransferDateBetween(startOfDay, endOfDay).stream().map(
			AutoTransferDto::from).collect(
			Collectors.toList());
	}

	public void createAutoTransfer(AutoTransferDto autoTransferDto) {
		autoTransferRepository.save(AutoTransfer.from(autoTransferDto));
	}

	public void increaseUnpaidCount(Long autoTransferId) {
		AutoTransfer autoTransfer = autoTransferRepository.findByAutoTransferId(autoTransferId).orElseThrow(() -> new BankingException(
			ErrorCode.NOT_EXIST_AUTO_TRANSFER));
		autoTransfer.updateUnpaidCount(autoTransfer.getUnpaidCount() + 1);
	}

	public void decreaseUnpaidCount(Long autoTransferId) {
		AutoTransfer autoTransfer = autoTransferRepository.findByAutoTransferId(autoTransferId).orElseThrow(() -> new BankingException(
			ErrorCode.NOT_EXIST_AUTO_TRANSFER));
		autoTransfer.updateUnpaidCount(autoTransfer.getUnpaidCount() - 1);
	}

	public void updateNextTransferDate(Long autoTransferId, LocalDateTime nextTransferDate) {
		AutoTransfer autoTransfer = autoTransferRepository.findByAutoTransferId(autoTransferId).orElseThrow(() -> new BankingException(ErrorCode.NOT_EXIST_AUTO_TRANSFER));
		autoTransfer.updateNextTransferDate(nextTransferDate);
	}
}
