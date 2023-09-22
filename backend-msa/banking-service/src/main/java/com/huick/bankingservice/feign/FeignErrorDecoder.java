package com.huick.bankingservice.feign;

import com.huick.bankingservice.global.error.ErrorCode;
import com.huick.bankingservice.global.error.exception.BusinessException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;

@Component
public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 400:
                break;
            case 404:
                if (methodKey.contains("getContractByContractId")) {
                    return new BusinessException(ErrorCode.NOT_EXIST_CONTRACT);
                }
                break;
            default:
                return new Exception(response.reason());
        }

        return null;
    }

}