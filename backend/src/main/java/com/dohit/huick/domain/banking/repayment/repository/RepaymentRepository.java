package com.dohit.huick.domain.banking.repayment.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import com.dohit.huick.domain.banking.repayment.entity.Repayment;

public interface RepaymentRepository extends JpaRepository<Repayment, Long> {
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	List<Repayment> findByContractId(Long contractId);

	@Lock(LockModeType.PESSIMISTIC_READ)
	Repayment findFirstByContractIdAndRepaymentDateGreaterThanEqualOrderByRepaymentDateAsc(Long contractId,
		LocalDateTime currentDate);

	@Lock(LockModeType.PESSIMISTIC_READ)
	Optional<Repayment> findFirstByContractIdAndRepaymentDateAfterOrderByRepaymentDateAsc(Long contractId,
		LocalDateTime currentTime);
}
