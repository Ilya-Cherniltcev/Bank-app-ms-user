package com.org.bankmsuser.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IncorrectInputDataException extends RuntimeException{
    public IncorrectInputDataException(){
        System.out.println("Incorrect input data");
    }
}
