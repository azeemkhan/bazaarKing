package com.tresshop.engine.base.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class RewardNotFoundException extends RuntimeException{
    private HttpStatus statusCode;
    private String errorMsg;
}
