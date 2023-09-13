package com.dohit.huick.domain.contract.repository;

import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import com.dohit.huick.domain.contract.entity.Contract;

public interface ContractRepository extends JpaRepository<Contract, Long> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Optional<Contract> findByContractId(Long contractId);
}
