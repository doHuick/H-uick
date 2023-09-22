package com.huick.bankingservice.domain.service;

import java.time.LocalDateTime;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.huick.bankingservice.domain.autotransfer.dto.AutoTransferDto;
import com.huick.bankingservice.domain.constant.ContractStatus;
import com.huick.bankingservice.domain.dto.ContractDto;
import com.huick.bankingservice.domain.transaction.dto.TransactionDto;
import com.huick.bankingservice.global.error.exception.TransferException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional
public class AutoTransferScheduler {
	private final BankingService bankingService;
	// private final ContractService contractService;	// 페인 클라이언트로 불러다가 쓰기
	// private final NotificationService notificationService;	// 카프카로 불러다 쓰기

	@Scheduled(cron = "0 0 9 * * ?", zone = "Asia/Seoul")
	public void transferAutomatically() {
		// 자동이체 테이블에서 이체일이 오늘인 것들의 데아터들을 불러옴
		List<AutoTransferDto> autoTransferDtos = bankingService.getAutoTransfersOfToday();
		Long autoTransferId = 0L;
		try {
			for (AutoTransferDto autoTransferDto : autoTransferDtos) {
				autoTransferId = autoTransferDto.getAutoTransferId();
				Long contractId = autoTransferDto.getContractId();
				ContractDto contractDto = ContractDto.builder().build(); // contractService.getContractByContractId(contractId);
				String senderAccountNumber = bankingService.getAccountByUserId(contractDto.getLesseeId())
					.getAccountNumber();
				String receiverAccountNumber = bankingService.getAccountByUserId(contractDto.getLessorId())
					.getAccountNumber();

				// 송금한다 -> Transaction Data 생성됨
				Long transactionId = bankingService.transferMoney(
					TransactionDto.of(senderAccountNumber, receiverAccountNumber, autoTransferDto.getAmount()));

				// 성공하면 미납 횟수를 본다 -> 미납 횟수가 1이상이면 -1 해주기
				if (autoTransferDto.getUnpaidCount() > 0) {
					bankingService.decreaseUnpaidCount(autoTransferId);
				}

				// NotificationDto notificationDto = notificationService.createNotification(
				// 	NotificationDto.of(contractDto.getLesseeId(), contractId, NotificationType.TRANSFER_SUCCESS));
				// notificationService.sendNotification(notificationDto);

				// Repayment Data 생성하기
				bankingService.createRepayment(contractId, transactionId);

				if (bankingService.isRepaymentDone(contractDto)) {    // 상환이 끝났으면 계약 상태를 `상환 완료` 로 업데이트 한다
					// contractService.updateContractStatus(contractId, ContractStatus.REPAYMENT_COMPLETED);
				} else {    // 상환이 끝나지 않았으면 다음 상환날짜를 구해서 업데이트 한다
					LocalDateTime today = autoTransferDto.getNextTransferDate();
					LocalDateTime nextTransferDate = today.plusMonths(1L);
					if (nextTransferDate.isAfter(contractDto.getDueDate())) { // 다음 자동이체일은 계약 기간 이후일 때
						nextTransferDate = contractDto.getDueDate();
					}
					long between = ChronoUnit.DAYS.between(today, nextTransferDate);

					boolean isStartDateLeapYear = Year.of(today.getYear()).isLeap();
					boolean isNextTransferDateLeapYear = Year.of(nextTransferDate.getYear()).isLeap();
					long amount = (long)(
						contractDto.getAmount() * (1 + contractDto.getRate()) / (isStartDateLeapYear ? 366 : 365)
							* between);
					if (today.getYear() != nextTransferDate.getYear()) {
						int nextTransferDateDayOfYear = nextTransferDate.getDayOfYear();
						amount = (long)(
							contractDto.getAmount() * (1 + contractDto.getRate()) / (isStartDateLeapYear ? 366 : 365)
								* (between
								- nextTransferDateDayOfYear)
								+ contractDto.getAmount() * (1 + contractDto.getRate()) / (isNextTransferDateLeapYear ?
								366 : 365)
								* nextTransferDateDayOfYear);
					}

					bankingService.updateNextTransfer(contractId, nextTransferDate, amount);
				}
			}
		} catch (TransferException b) {
			// 실패하면 미납 횟수를 증가시킨다
			bankingService.increaseUnpaidCount(autoTransferId);
		}
	}
}

