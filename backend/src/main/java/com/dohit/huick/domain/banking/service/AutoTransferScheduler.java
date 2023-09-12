package com.dohit.huick.domain.banking.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dohit.huick.domain.banking.autotransfer.dto.AutoTransferDto;
import com.dohit.huick.domain.banking.transaction.dto.TransactionDto;
import com.dohit.huick.domain.contract.constant.ContractStatus;
import com.dohit.huick.domain.contract.constant.TermUnit;
import com.dohit.huick.domain.contract.dto.ContractDto;
import com.dohit.huick.domain.contract.service.ContractService;
import com.dohit.huick.global.error.exception.TransferException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional
public class AutoTransferScheduler  {
	private final BankingService bankingService;
	private final ContractService contractService;

	@Scheduled(cron = "0 0 9 * * ?", zone = "Asia/Seoul")
	public void transferAutomatically() {
		// 자동이체 테이블에서 이체일이 오늘인 것들의 데아터들을 불러옴
		List<AutoTransferDto> autoTransferDtos = bankingService.getAutoTransfersOfToday();
		Long autoTransferId = 0L;
		try {
			for (int i = 0; i < autoTransferDtos.size(); i++) {
				AutoTransferDto autoTransferDto = autoTransferDtos.get(i);
				autoTransferId = autoTransferDto.getAutoTransferId();
				Long contractId = autoTransferDto.getContractId();
				ContractDto contractDto = contractService.getContractByContractId(contractId);
				String senderAccountNumber = bankingService.getAccountByUserId(contractDto.getLesseeId()).getAccountNumber();
				String receiverAccountNumber = bankingService.getAccountByUserId(contractDto.getLessorId()).getAccountNumber();;
				Long amount = contractDto.getRepaymentAmountPerOnce();

				// 송금한다 -> Transaction Data 생성됨
				Long transactionId = bankingService.transferMoney(TransactionDto.of(senderAccountNumber, receiverAccountNumber, amount));

				// 성공하면 미납 횟수를 본다 -> 미납 횟수가 1이상이면 -1 해주기
				if(autoTransferDto.getUnpaidCount() > 0) {
					bankingService.decreaseUnpaidCount(autoTransferId);
				}

				// Repayment Data 생성하기
				bankingService.createRepayment(contractId, transactionId);

				if(bankingService.isRepaymentDone(contractDto)) {	// 상환이 끝났으면 계약 상태를 `상환 완료` 로 업데이트 한다
					contractService.updateContractStatus(contractId, ContractStatus.REPAYMENT_COMPLETED);
				} else {	// 상환이 끝나지 않았으면 다음 상환날짜를 구해서 업데이트 한다
					LocalDateTime nextTransferDate = autoTransferDto.getNextTransferDate();
					if(contractDto.getTermUnit().equals(TermUnit.YEAR)) {
						nextTransferDate = nextTransferDate.plusYears(contractDto.getTerm());
					} else if(contractDto.getTermUnit().equals(TermUnit.MONTH)) {
						nextTransferDate = nextTransferDate.plusMonths(contractDto.getTerm());
					} else if(contractDto.getTermUnit().equals(TermUnit.WEEK)) {
						nextTransferDate = nextTransferDate.plusWeeks(contractDto.getTerm());
					} else if(contractDto.getTermUnit().equals(TermUnit.DAY)) {
						nextTransferDate = nextTransferDate.plusDays(contractDto.getTerm());
					}
					bankingService.updateNextTransferDate(contractId, nextTransferDate);
				}
			}
		} catch (TransferException b) {
			// 실패하면 미납 횟수를 증가시킨다
			bankingService.increaseUnpaidCount(autoTransferId);
		}
	}
}
