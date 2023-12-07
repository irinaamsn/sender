package com.spring.sender.exceptions;

import com.spring.sender.dto.ErrorDtoResponse;
import com.spring.sender.exceptions.base.GlobalSenderException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(GlobalSenderException.class)
    public ResponseEntity<ErrorDtoResponse> handleGlobalWeatherException(GlobalSenderException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(new ErrorDtoResponse(e.getStatus().value(), e.getErrorMessage()));
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorDtoResponse> handleSqlException(DataAccessException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDtoResponse(HttpStatus.BAD_REQUEST.value(),
                        "Invalid request"));
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorDtoResponse> handleUnknownException(Throwable e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDtoResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Unknown error"));
    }
}
