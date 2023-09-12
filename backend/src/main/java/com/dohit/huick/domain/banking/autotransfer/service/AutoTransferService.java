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
}
