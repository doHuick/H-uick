package com.dohit.huick.domain.banking.service;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dohit.huick.domain.banking.repayment.dto.RepaymentDto;
import com.dohit.huick.domain.banking.transaction.dto.TransactionDto;
import com.dohit.huick.domain.contract.constant.ContractStatus;
import com.dohit.huick.domain.contract.dto.ContractDto;
import com.dohit.huick.domain.contract.service.ContractService;
import com.dohit.huick.domain.notification.constant.NotificationType;
import com.dohit.huick.domain.notification.dto.NotificationDto;
import com.dohit.huick.domain.notification.service.NotificationService;
import com.dohit.huick.global.error.exception.TransferException;
import com.google.firebase.messaging.FirebaseMessagingException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional
public class AutoTransferScheduler {
	private final BankingService bankingService;
	private final ContractService contractService;
	private final NotificationService notificationService;

	@Scheduled(cron = "0 0 9 * * ?", zone = "Asia/Seoul")
	public void transferAutomatically() {
		// 자동이체 테이블에서 이체일이 오늘인 것들의 데아터들을 불러옴
		List<RepaymentDto> repaymentDtos = bankingService.findUnpaidAutoRepaymentUntilToday();
		Long repaymentId = 0L;

		try {
			for (RepaymentDto repaymentDto : repaymentDtos) {
				repaymentId = repaymentDto.getRepaymentId();
				Long contractId = repaymentDto.getContractId();

				ContractDto contractDto = contractService.getContractByContractId(contractId);
				if (contractDto.getUseAutoTransfer().equals("N")) {
					continue;
				}

				String senderAccountNumber = bankingService.getAccountByUserId(contractDto.getLesseeId())
					.getAccountNumber();
				String receiverAccountNumber = bankingService.getAccountByUserId(contractDto.getLessorId())
					.getAccountNumber();

				// 송금한다 -> Transaction Data 생성됨
				Long transactionId = bankingService.transferMoney(
					TransactionDto.of(senderAccountNumber, receiverAccountNumber, repaymentDto.getAmount()));

				// 성공하면 PAID 로 RepaymentStatus 변경
				bankingService.updateStatusPAIDAndTransactionId(repaymentId, transactionId);

				NotificationDto notificationDto = notificationService.createNotification(
					NotificationDto.of(contractDto.getLesseeId(), contractId, NotificationType.TRANSFER_SUCCESS));
				notificationService.sendNotification(notificationDto);

				if (bankingService.isRepaymentDone(contractDto)) {    // 상환이 끝났으면 계약 상태를 `상환 완료` 로 업데이트 한다
					contractService.updateContractStatus(contractId, ContractStatus.REPAYMENT_COMPLETED);
				}
			}
		} catch (TransferException b) {
			// 실패하면 미납 횟수를 증가시킨다
		} catch (FirebaseMessagingException ignore) {
		}
	}
}
