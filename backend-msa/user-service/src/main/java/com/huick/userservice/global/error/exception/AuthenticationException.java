package com.huick.userservice.global.error.exception;

import com.huick.userservice.global.error.ErrorCode;

public class AuthenticationException extends BusinessException{
	public AuthenticationException(ErrorCode errorCode){
		super(errorCode);
	}
}