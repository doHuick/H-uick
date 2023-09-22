package com.huick.bankingservice.domain.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huick.bankingservice.domain.account.dto.AccountDto;
import com.huick.bankingservice.domain.account.service.AccountService;
import com.huick.bankingservice.domain.autotransfer.dto.AutoTransferDto;
import com.huick.bankingservice.domain.autotransfer.service.AutoTransferService;
import com.huick.bankingservice.domain.bank.service.BankService;
import com.huick.bankingservice.domain.constant.ContractStatus;
import com.huick.bankingservice.domain.dto.ContractDto;
import com.huick.bankingservice.domain.repayment.dto.RepaymentDto;
import com.huick.bankingservice.domain.repayment.service.RepaymentService;
import com.huick.bankingservice.domain.transaction.dto.TransactionDto;
import com.huick.bankingservice.domain.transaction.service.TransactionService;
import com.huick.bankingservice.global.error.ErrorCode;
import com.huick.bankingservice.global.error.exception.BankingException;
import com.huick.bankingservice.global.error.exception.TransferException;

import lombok.RequiredArgsConstructor;


@Service
@Transactional
@RequiredArgsConstructor
public class BankingService {

	private final AccountService accountService;
	private final TransactionService transactionService;
	private final BankService bankService;
	private final AutoTransferService autoTransferService;
	private final RepaymentService repaymentService;

	// private final ContractService contractService; //카프카로 불러다 쓰기

	public void createAccount(Long userId) {
		accountService.createAccount(userId);
	}

	public void transferRandomMoney(String accountNumber) {
		// 랜덤한 잔액 생성하기
		SecureRandom secureRandom = new SecureRandom();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(secureRandom.nextInt(900000) + 100000);
		int random = secureRandom.nextInt(3) + 3;
		stringBuilder.append("0".repeat(random));
		Long randomMoney = Long.parseLong(stringBuilder.toString());

		// 어카운트 넘버로 어카운트를 찾아와서 잔액을 변경함
		accountService.updateBalance(accountNumber, randomMoney);

		// 트랜잭션 데이터를 생성
		TransactionDto transactionDto = TransactionDto.of("100408000000", accountNumber, randomMoney);
		transactionService.createTransaction(transactionDto);
	}

	public AccountDto getAccountByUserId(Long userId) {
		AccountDto account = accountService.getAccountsByUserId(userId).get(0);
		return AccountDto.of(account, bankService.getBankByBankCode(account.getBankCode()));
	}

	public Long transferMoney(TransactionDto transactionDto) throws BankingException {
		// from 계좌 가져옴
		AccountDto senderAccountDto = accountService.getAccountByAccountNumber(transactionDto.getSenderAccountNumber());
		if (senderAccountDto.getBalance() < transactionDto.getAmount()) {
			throw new TransferException(ErrorCode.NOT_ENOUGH_MONEY);
		}

		// from 계좌에서 돈을 뺌
		accountService.updateBalance(senderAccountDto.getAccountNumber(), -transactionDto.getAmount());

		// to 계좌  가져와서 돈을 넣음
		AccountDto receiverAccountDto = accountService.getAccountByAccountNumber(
			transactionDto.getReceiverAccountNumber());
		accountService.updateBalance(receiverAccountDto.getAccountNumber(), transactionDto.getAmount());

		// 트랜잭션 데이터를 생성함
		return transactionService.createTransaction(transactionDto);
	}

	public List<TransactionDto> getTransactionsByUserId(Long userId) {
		String accountNumber = getAccountByUserId(userId).getAccountNumber();
		return transactionService.getTransactionsByUserId(accountNumber);
	}

	public List<AutoTransferDto> getAutoTransfersOfToday() {
		return autoTransferService.getAutoTransfersOfToday();
	}

	public void increaseUnpaidCount(Long autoTransferId) {
		autoTransferService.increaseUnpaidCount(autoTransferId);
	}

	public void decreaseUnpaidCount(Long autoTransferId) {
		autoTransferService.decreaseUnpaidCount(autoTransferId);
	}

	public void createRepayment(Long contractId, Long transactionId) {
		Integer repaymentNumber = repaymentService.getRepaymentsByContractId(contractId).size() + 1;
		repaymentService.createRepayment(RepaymentDto.of(contractId, transactionId, repaymentNumber));
	}

	public Boolean isRepaymentDone(ContractDto contractDto) {
		List<RepaymentDto> repaymentDtos = repaymentService.getRepaymentsByContractId(contractDto.getContractId());
		Long totalRepaymentAmount = repaymentDtos.stream()
			.mapToLong(repaymentDto -> transactionService.getTransactionByTransactionId(repaymentDto.getTransactionId())
				.getAmount())
			.sum();

		Long totalAmount = 0L;
		LocalDateTime startDate = contractDto.getStartDate();
		LocalDateTime dueDate = contractDto.getDueDate();

		while (!startDate.isEqual(dueDate)) {
			LocalDateTime endDateOfYearOfStartDate = LocalDateTime.of(startDate.getYear() + 1, 0, 0, 0, 0);
			if (endDateOfYearOfStartDate.isAfter(dueDate)) { // 이번년도에 계약 상환이 끝나야 함
				endDateOfYearOfStartDate = dueDate;
			}
			totalAmount +=
				(long)(contractDto.getAmount() * (1 + contractDto.getRate()) / (Year.of(startDate.getYear()).isLeap() ?
					366 : 365) * ChronoUnit.DAYS.between(startDate, endDateOfYearOfStartDate));
			startDate = endDateOfYearOfStartDate;
		}

		return Objects.equals(totalAmount, totalRepaymentAmount);
	}

	public void updateNextTransfer(Long autoTransferId, LocalDateTime nextTransferDate, Long amount) {
		autoTransferService.updateNextTransfer(autoTransferId, nextTransferDate, amount);
	}

	public void repay(Long contractId, Long amount) {
		// 이체시키고
		ContractDto contractDto = ContractDto.builder().build();	// contractId로 페인클라이언트 이용해서 ContractDto 가져오기
		Long transactionId = transferMoney(
			TransactionDto.of(getAccountByUserId(contractDto.getLesseeId()).getAccountNumber(),
				getAccountByUserId(contractDto.getLessorId()).getAccountNumber(), amount));
		// 상환데이터 넣고
		createRepayment(contractDto.getContractId(), transactionId);
		// 상환 끝났는지 체크하기
		if (isRepaymentDone(contractDto)) {
			// contractService.updateContractStatus(contractDto.getContractId(), ContractStatus.REPAYMENT_COMPLETED);
		}
	}

	public List<AutoTransferDto> getAutoTransfersAfter3Days() {
		return autoTransferService.getAutoTransfersAfter3Days();
	}

	public List<AutoTransferDto> getOverdueAutoTransfers() {
		return autoTransferService.getOverdueAutoTransfers();
	}
}


