package com.org.bankmsuser.exception;

public class ServiceException extends RuntimeException{
    public ServiceException(ErrorCode errorCode, Object... args) {
        super(errorCode.format(args));
    }
}
