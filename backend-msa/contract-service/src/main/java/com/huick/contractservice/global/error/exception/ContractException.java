package com.huick.contractservice.global.error.exception;

import com.huick.contractservice.global.error.ErrorCode;

public class ContractException extends BusinessException{
	public ContractException(ErrorCode errorCode) {
		super(errorCode);
	}
}
