package com.codilien.hostelmanagementsystem.Response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BaseResponseWithList<T> extends BaseResponse{
    private List<T> data;
}
