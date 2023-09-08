package com.dohit.huick.global.error.exception;

import com.dohit.huick.global.error.ErrorCode;

public class BankingException extends BusinessException{
	public BankingException(ErrorCode errorCode) {
		super(errorCode);
	}
}
