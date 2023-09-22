package com.huick.contractservice.domain.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import com.huick.contractservice.domain.entity.Contract;

public interface ContractRepository extends JpaRepository<Contract, Long> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Optional<Contract> findByContractId(Long contractId);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	List<Contract> findByLesseeId(Long lesseeId);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	List<Contract> findByLessorId(Long lessorId);
}
