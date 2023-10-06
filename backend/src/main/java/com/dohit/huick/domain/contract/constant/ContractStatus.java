package com.dohit.huick.domain.contract.constant;

public enum ContractStatus {
	BEFORE_EXECUTION("체결 전"),
	EXECUTION_COMPLETED("체결 완료"),
	REPAYMENT_COMPLETED("상환 완료"),
	TERMINATION("파기");


	private final String contractStatus;

	ContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}
}
