package com.dohit.huick.domain.banking.autotransfer.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import com.dohit.huick.domain.banking.autotransfer.entity.AutoTransfer;

public interface AutoTransferRepository extends JpaRepository<AutoTransfer, Long> {
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	List<AutoTransfer> findByNextTransferDateBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Optional<AutoTransfer> findByAutoTransferId(Long autoTransferId);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	List<AutoTransfer> findByUnpaidCountGreaterThan(Integer unpaidCount);
}
