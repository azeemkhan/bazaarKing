package com.tresshop.engine.base.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class ShareAndReferException extends RuntimeException{
    private HttpStatus status;
    private String errorMsg;
}
