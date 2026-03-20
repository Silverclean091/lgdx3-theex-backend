package com.lg.theex.global.exception.exceptionType;


import com.lg.theex.global.exception.CustomException;
import com.lg.theex.global.exception.ErrorCode;

public class BadRequestException extends CustomException {
    public BadRequestException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}