package com.huick.bankingservice.global.error.exception;

import com.huick.bankingservice.global.error.ErrorCode;

public class BankingException extends BusinessException{
	public BankingException(ErrorCode errorCode) {
		super(errorCode);
	}
}
