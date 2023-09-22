package com.huick.userservice.global.error.exception;

import com.huick.userservice.global.error.ErrorCode;

public class ContractException extends BusinessException{
	public ContractException(ErrorCode errorCode) {
		super(errorCode);
	}
}
