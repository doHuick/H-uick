package com.dohit.huick.api.banking.repayment.dto;

import lombok.Builder;
import lombok.Getter;

public class RepaymentApiDto {

	@Getter
	public static class Request {
		Long contractId;
		Long amount;

		@Builder
		public Request(Long contractId, Long amount) {
			this.contractId = contractId;
			this.amount = amount;
		}
	}
}
