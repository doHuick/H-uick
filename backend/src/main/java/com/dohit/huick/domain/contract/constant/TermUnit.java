package com.dohit.huick.domain.contract.constant;

public enum TermUnit {
	DAY("일"),
	WEEK("주"),
	MONTH("월"),
	YEAR("연");

	private final String termUnit;

	TermUnit(String termUnit) {
		this.termUnit = termUnit;
	}
}
