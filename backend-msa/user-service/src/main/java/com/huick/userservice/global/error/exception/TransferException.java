package com.huick.userservice.global.error.exception;

import com.huick.userservice.global.error.ErrorCode;

public class TransferException extends BankingException {
	public TransferException(ErrorCode errorCode) {
		super(errorCode);
	}
}
