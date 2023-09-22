package com.huick.userservice.global.error.exception;

import com.huick.userservice.global.error.ErrorCode;

public class BankingException extends BusinessException{
	public BankingException(ErrorCode errorCode) {
		super(errorCode);
	}
}
