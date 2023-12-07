package com.spring.sender.exceptions;


import com.spring.sender.exceptions.base.GlobalSenderException;
import org.springframework.http.HttpStatus;

public class NotFoundException extends GlobalSenderException {
    public NotFoundException(HttpStatus status, String errorMessage, long timestamp) {
        super(status, errorMessage, timestamp);
    }
}
