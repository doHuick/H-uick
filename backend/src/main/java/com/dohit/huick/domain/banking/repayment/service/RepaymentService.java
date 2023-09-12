package com.dohit.huick.domain.banking.repayment.service;

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

	public void createRepayment(RepaymentDto repaymentDto) {
		repaymentRepository.save(Repayment.from(repaymentDto));
	}
}
