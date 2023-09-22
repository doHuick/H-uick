package com.huick.contractservice.global.error.exception;

import com.huick.contractservice.global.error.ErrorCode;

public class BankingException extends BusinessException{
	public BankingException(ErrorCode errorCode) {
		super(errorCode);
	}
}
