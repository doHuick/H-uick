package com.huick.contractservice.global.error.exception;

import com.huick.contractservice.global.error.ErrorCode;

public class AuthenticationException extends BusinessException{
	public AuthenticationException(ErrorCode errorCode){
		super(errorCode);
	}
}