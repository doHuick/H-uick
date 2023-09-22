package com.huick.bankingservice.global.error.exception;

import com.huick.bankingservice.global.error.ErrorCode;

public class ContractException extends BusinessException{
	public ContractException(ErrorCode errorCode) {
		super(errorCode);
	}
}
