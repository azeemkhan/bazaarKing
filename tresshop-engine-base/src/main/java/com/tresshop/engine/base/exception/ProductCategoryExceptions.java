package com.tresshop.engine.base.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ProductCategoryExceptions {
    @AllArgsConstructor
    @Getter
    public static class ProductCategoryException extends RuntimeException {
        private HttpStatus status;
        private String errorMsg;
    }
}
