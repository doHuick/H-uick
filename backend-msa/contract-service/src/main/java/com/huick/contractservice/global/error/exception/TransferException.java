package com.huick.contractservice.global.error.exception;

import com.huick.contractservice.global.error.ErrorCode;

public class TransferException extends BankingException {
	public TransferException(ErrorCode errorCode) {
		super(errorCode);
	}
}
