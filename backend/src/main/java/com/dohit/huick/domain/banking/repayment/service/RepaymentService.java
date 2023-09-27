package com.dohit.huick.domain.banking.repayment.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dohit.huick.domain.banking.repayment.dto.RepaymentDto;
import com.dohit.huick.domain.banking.repayment.entity.Repayment;
import com.dohit.huick.domain.banking.repayment.repository.RepaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RepaymentService {
	private final RepaymentRepository repaymentRepository;

	// @Lock(LockModeType.PESSIMISTIC_WRITE)
	// public void createRepayment(RepaymentDto repaymentDto) {
	//     repaymentRepository.save(Repayment.from(repaymentDto));
	// }

	public List<RepaymentDto> getRepaymentsByContractId(Long contractId) {
		return repaymentRepository.findByContractId(contractId).stream().map(RepaymentDto::from).collect(
			Collectors.toList());
	}
}
