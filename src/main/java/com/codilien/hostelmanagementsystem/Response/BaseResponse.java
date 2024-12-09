package com.codilien.hostelmanagementsystem.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponse<T> {
    protected int code;
    protected String message;
    protected boolean status;
    private T data;
}
