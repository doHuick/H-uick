package com.huick.bankingservice.api.repayment.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class RepaymentApiDto {

	@Getter
	@NoArgsConstructor
	@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
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
