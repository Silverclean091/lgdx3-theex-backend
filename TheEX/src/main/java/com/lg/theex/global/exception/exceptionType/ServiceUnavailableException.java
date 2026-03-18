package com.lg.theex.global.exception.exceptionType;

import com.lg.theex.global.exception.CustomException;
import com.lg.theex.global.exception.ErrorCode;

public class ServiceUnavailableException extends CustomException {
    public ServiceUnavailableException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
