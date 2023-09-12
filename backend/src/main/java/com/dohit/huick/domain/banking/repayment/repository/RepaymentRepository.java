package com.dohit.huick.domain.banking.repayment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dohit.huick.domain.banking.repayment.entity.Repayment;

public interface RepaymentRepository extends JpaRepository <Repayment, Long> {
	List<Repayment> findByContractId(Long contractId);
}
