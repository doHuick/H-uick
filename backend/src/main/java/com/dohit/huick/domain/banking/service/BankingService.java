package com.dohit.huick.domain.banking.service;

import java.security.SecureRandom;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dohit.huick.domain.banking.account.dto.AccountDto;
import com.dohit.huick.domain.banking.account.service.AccountService;
import com.dohit.huick.domain.banking.bank.service.BankService;
import com.dohit.huick.domain.banking.repayment.constant.RepaymentStatus;
import com.dohit.huick.domain.banking.repayment.dto.RepaymentDto;
import com.dohit.huick.domain.banking.repayment.service.RepaymentService;
import com.dohit.huick.domain.banking.transaction.dto.TransactionDto;
import com.dohit.huick.domain.banking.transaction.service.TransactionService;
import com.dohit.huick.domain.contract.constant.ContractStatus;
import com.dohit.huick.domain.contract.dto.ContractDto;
import com.dohit.huick.domain.contract.service.ContractService;
import com.dohit.huick.global.error.ErrorCode;
import com.dohit.huick.global.error.exception.BankingException;
import com.dohit.huick.global.error.exception.TransferException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BankingService {

	private final AccountService accountService;
	private final TransactionService transactionService;
	private final BankService bankService;
	private final RepaymentService repaymentService;

	private final ContractService contractService;

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
		AccountDto accountDto = accountService.getAccountsByUserId(userId).get(0);
		return AccountDto.of(accountDto, bankService.getBankByBankCode(accountDto.getBankCode()));
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

	public List<RepaymentDto> getPaymentsOfToday() {
		return repaymentService.getRepaymentsOfToday();
	}

	public Boolean isRepaymentDone(ContractDto contractDto) {
		int unpaidCount = repaymentService.countRepaymentsByContractIdAndStatus(contractDto.getContractId(),
			RepaymentStatus.UNPAID);

		return unpaidCount == 0;
	}

	public void repay(ContractDto contractDto, Long amount) {
		// 이체시키고
		Long transactionId = transferMoney(
			TransactionDto.of(getAccountByUserId(contractDto.getLesseeId()).getAccountNumber(),
				getAccountByUserId(contractDto.getLessorId()).getAccountNumber(), amount));
		// 상환데이터 넣고

		// 상환 끝났는지 체크하기
		if (isRepaymentDone(contractDto)) {
			contractService.updateContractStatus(contractDto.getContractId(), ContractStatus.REPAYMENT_COMPLETED);
		}
	}

	public void updateStatusPAIDAndTransactionId(Long repaymentId, Long transactionId) {
		repaymentService.updateStatusPAIDAndTransactionId(repaymentId, transactionId);
	}

	public List<RepaymentDto> getRepaymentsAfter3Days() {
		return repaymentService.getRepaymentsAfter3Days();
	}

	public List<RepaymentDto> getOverdueRepayments() {
		return repaymentService.getOverdueRepayments();
	}
}
