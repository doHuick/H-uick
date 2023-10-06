package com.dohit.huick.global.error.exception;

import com.dohit.huick.global.error.ErrorCode;

public class AuthenticationException extends BusinessException{
	public AuthenticationException(ErrorCode errorCode){
		super(errorCode);
	}
}