package com.codilien.hostelmanagementsystem.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionResponse {
    private String timestamp;
    private String error;
    private String message;
    private String exceptionType;
    private String path;
    private int status;
}