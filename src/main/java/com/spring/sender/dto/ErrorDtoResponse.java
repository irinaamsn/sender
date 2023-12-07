package com.spring.sender.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorDtoResponse {
    private int status;
    private String message;
}
