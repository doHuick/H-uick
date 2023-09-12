package com.dohit.huick.global.error.exception;

import com.dohit.huick.global.error.ErrorCode;

public class ContractException extends BusinessException{
	public ContractException(ErrorCode errorCode) {
		super(errorCode);
	}
}
