package com.org.bankmsuser.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DataExistException extends RuntimeException{
    public DataExistException(){
        System.out.println("User exists already!");
    }
}
