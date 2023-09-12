package com.dohit.huick.domain.contract.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dohit.huick.domain.contract.entity.Contract;

public interface ContractRepository extends JpaRepository<Contract, Long> {
	Optional<Contract> findByContractId(Long contractId);
}
