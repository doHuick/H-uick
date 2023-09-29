package com.dohit.huick.domain.banking.repayment.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import com.dohit.huick.domain.banking.repayment.constant.RepaymentStatus;
import com.dohit.huick.domain.banking.repayment.entity.Repayment;

public interface RepaymentRepository extends JpaRepository<Repayment, Long> {
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	List<Repayment> findByContractId(Long contractId);

	@Lock(LockModeType.PESSIMISTIC_READ)
	Optional<Repayment> findTopByContractIdAndStatusOrderByTimeAsc(Long contractId, RepaymentStatus status);

	@Lock(LockModeType.PESSIMISTIC_READ)
	int countRepaymentsByContractIdAndStatus(Long contractId, RepaymentStatus repaymentStatus);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	List<Repayment> findByRepaymentDateBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);

	Optional<Repayment> getRepaymentByRepaymentId(Long repaymentId);

	Optional<Repayment> getRepaymentsByRepaymentDateBeforeAndStatus(LocalDateTime today, RepaymentStatus status);
}
