package com.tresshop.engine.base.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class CustomersException {
    @AllArgsConstructor
    @Getter
    public static class CustomerNotFoundException extends RuntimeException {
        private HttpStatus status;
        private String errorMsg;
    }
    @AllArgsConstructor
    @Getter
    public static class CustomerBlockedException extends RuntimeException {
        private HttpStatus status;
        private String errorMsg;
    }
    @AllArgsConstructor
    @Getter
    public static class CustomerException extends RuntimeException {
        private HttpStatus status;
        private String errorMsg;
    }
    @AllArgsConstructor
    @Getter
    public static class CustomerAlreadyRegisteredException extends RuntimeException{
        private HttpStatus status;
        private String errorMsg;
    }

}
