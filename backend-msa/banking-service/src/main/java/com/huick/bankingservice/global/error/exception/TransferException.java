package com.huick.bankingservice.global.error.exception;

import com.huick.bankingservice.global.error.ErrorCode;

public class TransferException extends BankingException {
	public TransferException(ErrorCode errorCode) {
		super(errorCode);
	}
}
