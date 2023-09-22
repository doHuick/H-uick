package com.huick.bankingservice.global.error.exception;

import com.huick.bankingservice.global.error.ErrorCode;

public class AuthenticationException extends BusinessException{
	public AuthenticationException(ErrorCode errorCode){
		super(errorCode);
	}
}