package com.huick.userservice.feign;

import org.springframework.stereotype.Component;

import com.huick.userservice.global.error.ErrorCode;
import com.huick.userservice.global.error.exception.BusinessException;

import feign.Response;
import feign.codec.ErrorDecoder;

@Component
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 400:
                break;
            case 404:
                if (methodKey.contains("getAccountByUserId")) {
                    return new BusinessException(ErrorCode.NOT_EXIST_USER);
                }
                break;
            default:
                return new Exception(response.reason());
        }

        return null;
    }
}