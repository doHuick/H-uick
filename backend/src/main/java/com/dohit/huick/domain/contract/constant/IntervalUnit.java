package com.dohit.huick.domain.contract.constant;

public enum IntervalUnit {
	DAY("일"),
	WEEK("주"),
	MONTH("월"),
	YEAR("연");

	private final String intervalUnit;

	IntervalUnit(String intervalUnit) {
		this.intervalUnit = intervalUnit;
	}
}
