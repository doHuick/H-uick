package com.dohit.huick.global.error.exception;

import com.dohit.huick.global.error.ErrorCode;

public class TransferException extends BankingException {
	public TransferException(ErrorCode errorCode) {
		super(errorCode);
	}
}
