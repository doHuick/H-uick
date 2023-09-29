package com.dohit.huick.domain.banking.repayment.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dohit.huick.domain.banking.repayment.constant.RepaymentStatus;
import com.dohit.huick.domain.banking.repayment.dto.RepaymentDto;
import com.dohit.huick.domain.banking.repayment.entity.Repayment;
import com.dohit.huick.domain.banking.repayment.repository.RepaymentRepository;
import com.dohit.huick.domain.contract.dto.ContractDto;
import com.dohit.huick.global.error.ErrorCode;
import com.dohit.huick.global.error.exception.BankingException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RepaymentService {
	private final RepaymentRepository repaymentRepository;

	public List<RepaymentDto> getRepaymentsByContractId(Long contractId) {
		return repaymentRepository.findByContractId(contractId).stream().map(RepaymentDto::from).collect(
			Collectors.toList());
	}

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	public void createAllRepayment(ContractDto contractDto) {
		// 이번 상환 날짜
		LocalDateTime payday = contractDto.getStartDate();
		// 다음 상환 날짜
		LocalDateTime nextPayday = null;
		// 최종 상환 날짜
		LocalDateTime dueDate = contractDto.getDueDate();
		// 상환 횟수
		int count = 1;
		long sumOfAmount = 0L;
		float rate = contractDto.getRate() / 100;

		// 이번 상환 날짜가 최종 상환 날짜가 될 때까지 반복
		while (!payday.isEqual(dueDate)) {

			// 다음 상환 날짜는 이번 상환 날짜로 부터 한 달 뒤
			nextPayday = payday.plusMonths(1L);
			// 만약 다음 상환 일이 상환 시작 일보다 늦으면 다음 상환일을 상환 시작일로 변경
			// ex) 1월 31일 -> 2월 28일 -> 3월 28일(X) 3월 31일(O)
			if (nextPayday.getDayOfMonth() < contractDto.getStartDate().getDayOfMonth()) {
				int day = nextPayday.getMonth().length(nextPayday.toLocalDate().isLeapYear());
				nextPayday.withDayOfMonth(Math.min(nextPayday.getMonth().length(nextPayday.toLocalDate().isLeapYear()),
					contractDto.getStartDate().getDayOfMonth()));
			}

			// 다음 상환 날짜가 최종 상환 날짜보다 나중이면 다음 상환 날짜를 최종 상환 날짜로 변경
			if (nextPayday.isAfter(dueDate)) {
				nextPayday = dueDate;
			}

			// 그래서 다음 상환이 최종 상환이면 (총 갚아야 하는 금액 - 현재까지 갚은 금액)을 갚기
			if (nextPayday.isEqual(dueDate)) {
				// 총 갚아야 하는 금액 (원금)
				long totalAmount = contractDto.getAmount();
				// 최초 상환 날짜
				LocalDateTime startDate = contractDto.getStartDate();

				// 상환 기간을 구하기 위해 최초 상환 날짜가 최종 상환 날짜가 될 때까지 반복
				while (!startDate.isEqual(dueDate)) {
					// 이번 년도 마지막 날
					LocalDateTime endDateOfYearOfStartDate = LocalDateTime.of(startDate.getYear() + 1, 1, 1, 0, 0);

					// 이번년도에 계약 상환이 끝닌디
					if (endDateOfYearOfStartDate.isAfter(dueDate)) {
						endDateOfYearOfStartDate = dueDate;
					}
					// 총 갚아야 하는 금액에 이자 더하기
					totalAmount += (long)(
						contractDto.getAmount() * rate / (Year.of(startDate.getYear()).isLeap() ?
							366 : 365) * ChronoUnit.DAYS.between(startDate, endDateOfYearOfStartDate));

					//
					startDate = endDateOfYearOfStartDate;
				}

				repaymentRepository.save(
					Repayment.of(contractDto.getContractId(), totalAmount - sumOfAmount, dueDate, count++));

				payday = dueDate;
				continue;
			}

			long between = ChronoUnit.DAYS.between(payday, nextPayday);
			boolean isStartDateLeapYear = Year.of(payday.getYear()).isLeap();
			boolean isNextTransferDateLeapYear = Year.of(nextPayday.getYear()).isLeap();
			long amount = (long)(
				contractDto.getAmount() * rate / (isStartDateLeapYear ? 366 : 365)
					* between);

			if (payday.getYear() != nextPayday.getYear()) {
				int nextTransferDateDayOfYear = nextPayday.getDayOfYear();
				amount = (long)(
					contractDto.getAmount() * rate / (isStartDateLeapYear ? 366 : 365)
						* (between - nextTransferDateDayOfYear)
						+ contractDto.getAmount() * rate / (isNextTransferDateLeapYear ?
						366 : 365) * nextTransferDateDayOfYear);
			}

			repaymentRepository.save(Repayment.of(contractDto.getContractId(), amount, nextPayday, count++));

			payday = nextPayday;
			sumOfAmount += amount;
		}
	}

	public RepaymentDto findCurrentRepaymentByContractId(Long contractId) {
		return repaymentRepository.findFirstByContractIdAndRepaymentDateAfterOrderByRepaymentDateAsc(contractId,
			LocalDateTime.now()).map(RepaymentDto::from).orElse(null);
	}

	public int countRepaymentsByContractIdAndStatus(Long contractId, RepaymentStatus repaymentStatus) {
		return repaymentRepository.countRepaymentsByContractIdAndStatus(contractId, repaymentStatus);
	}

	public List<RepaymentDto> getRepaymentsOfToday() {
		LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
		LocalDateTime endOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

		return repaymentRepository.findByRepaymentDateBetween(startOfDay, endOfDay).stream().map(
			RepaymentDto::from).collect(
			Collectors.toList());
	}

	public void updateStatusPAIDAndTransactionId(Long repaymentId, Long transactionId) {
		Repayment repayment = repaymentRepository.getRepaymentByRepaymentId(repaymentId)
			.orElseThrow(() -> new BankingException(ErrorCode.NOT_EXIST_REPAYMENT));

		repayment.updateStatusPAIDAndTransactionId(transactionId);
		;
	}

	public List<RepaymentDto> getRepaymentsAfter3Days() {
		LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now().plusDays(3L), LocalTime.MIN);
		LocalDateTime endOfDay = LocalDateTime.of(LocalDate.now().plusDays(3L), LocalTime.MAX);

		return repaymentRepository.findByRepaymentDateBetween(startOfDay, endOfDay).stream().map(
			RepaymentDto::from).collect(
			Collectors.toList());
	}

	public List<RepaymentDto> getOverdueRepayments() {
		LocalDateTime today = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

		return repaymentRepository.getRepaymentsByRepaymentDateBeforeAndStatus(today, RepaymentStatus.UNPAID)
			.stream().map(RepaymentDto::from).collect(Collectors.toList());
	}
}