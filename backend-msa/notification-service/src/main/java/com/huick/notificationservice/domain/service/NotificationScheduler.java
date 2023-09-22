package com.huick.notificationservice.domain.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.huick.notificationservice.domain.constant.NotificationType;
import com.huick.notificationservice.domain.dto.AutoTransferDto;
import com.huick.notificationservice.domain.dto.NotificationDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional
public class NotificationScheduler {
	// private final BankingService bankingService;
	private final NotificationService notificationService;
	// private final ContractService contractService;

	@Scheduled(cron = "0 0 10 * * ?", zone = "Asia/Seoul")
	public void sendNotification() {
		// 이체 에정 3일전에 알림 보내기

		// List<AutoTransferDto> autoTransferAfter3Days = bankingService.getAutoTransfersAfter3Days(); // 페인 클라이언트 사용해서 가져오기
		List<AutoTransferDto> autoTransferAfter3Days = new ArrayList<>();

		// 이 알림에 대해 create 호출
		List<NotificationDto> notificationDtos = new ArrayList<>();

		// 페인 클라이언트 사용해서 가져오기
		// autoTransferAfter3Days.forEach(a -> notificationDtos.add(notificationService.createNotification(NotificationDto.of(a.getContractId(), contractService.getContractByContractId(a.getContractId()).getLesseeId(),
		// 	NotificationType.UPCOMING_TRANSFER))));
		notificationDtos.forEach(n -> {
			try {
				notificationService.sendNotification(n);
			} catch (FirebaseMessagingException ignored) {
			}
		});

		notificationDtos.clear();

		// 연체되었다면 매일매일
		// List<AutoTransferDto> overdueAutoTransfer = bankingService.getOverdueAutoTransfers();	// 페인 클라이언트 사용해서 가져오기

		List<AutoTransferDto> overdueAutoTransfer = new ArrayList<>();

		// 페인 클라이언트 사용해서 가져오기
		// autoTransferAfter3Days.forEach(a -> notificationDtos.add(notificationService.createNotification(NotificationDto.of(a.getContractId(), contractService.getContractByContractId(a.getContractId()).getLesseeId(),
		// 	NotificationType.OVERDUE))));
		notificationDtos.forEach(n -> {
			try {
				notificationService.sendNotification(n);
			} catch (FirebaseMessagingException ignored) {
			}
		});
	}
}
