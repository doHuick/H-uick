package com.dohit.huick.domain.banking.autotransfer.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dohit.huick.domain.banking.autotransfer.entity.AutoTransfer;

public interface AutoTransferRepository extends JpaRepository<AutoTransfer, Long> {
	List<AutoTransfer> findByNextTransferDateBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);

	Optional<AutoTransfer> findByAutoTransferId(Long autoTransferId);
}
