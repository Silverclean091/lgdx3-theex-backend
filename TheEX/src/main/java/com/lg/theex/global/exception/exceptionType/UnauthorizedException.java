package com.lg.theex.global.exception.exceptionType;

import com.lg.theex.global.exception.CustomException;
import com.lg.theex.global.exception.ErrorCode;

public class UnauthorizedException extends CustomException {
    public UnauthorizedException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
