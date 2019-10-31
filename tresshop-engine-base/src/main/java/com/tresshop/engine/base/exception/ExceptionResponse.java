package com.tresshop.engine.base.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ExceptionResponse {
    private int statusCode;
    private String errorMsg;
}
