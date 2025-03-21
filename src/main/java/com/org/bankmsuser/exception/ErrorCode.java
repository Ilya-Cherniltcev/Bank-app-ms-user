package com.org.bankmsuser.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    INPUT_DATA_NOT_VALID("Incorrect input data"),
    USER_NOT_FOUND("User is not found"),
    USER_EXISTS("User exists already");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String format(Object... args) {
        return String.format(message, args);
    }

}
