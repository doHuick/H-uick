package com.dohit.huick.domain.notification.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dohit.huick.domain.banking.autotransfer.dto.AutoTransferDto;
import com.dohit.huick.domain.banking.service.BankingService;
import com.dohit.huick.domain.contract.service.ContractService;
import com.dohit.huick.domain.notification.constant.NotificationType;
import com.dohit.huick.domain.notification.dto.NotificationDto;
import com.google.firebase.messaging.FirebaseMessagingException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional
public class NotificationScheduler {
	private final BankingService bankingService;
	private final NotificationService notificationService;
	private final ContractService contractService;

	@Scheduled(cron = "0 0 10 * * ?", zone = "Asia/Seoul")
	public void sendNotification() {
		// 이체 에정 3일전에 알림 보내기
		List<AutoTransferDto> autoTransferAfter3Days = bankingService.getAutoTransfersAfter3Days();
		// 이 알림에 대해 create 호출
		List<NotificationDto> notificationDtos = new ArrayList<>();
		autoTransferAfter3Days.forEach(a -> notificationDtos.add(notificationService.createNotification(NotificationDto.of(a.getContractId(), contractService.getContractByContractId(a.getContractId()).getLesseeId(),
			NotificationType.UPCOMING_TRANSFER))));
		notificationDtos.forEach(n -> {
			try {
				notificationService.sendNotification(n);
			} catch (FirebaseMessagingException ignored) {
			}
		});

		notificationDtos.clear();

		// 연체되었다면 매일매일
		List<AutoTransferDto> overdueAutoTransfer = bankingService.getOverdueAutoTransfers();
		autoTransferAfter3Days.forEach(a -> notificationDtos.add(notificationService.createNotification(NotificationDto.of(a.getContractId(), contractService.getContractByContractId(a.getContractId()).getLesseeId(),
			NotificationType.OVERDUE))));
		notificationDtos.forEach(n -> {
			try {
				notificationService.sendNotification(n);
			} catch (FirebaseMessagingException ignored) {
			}
		});
	}
}
