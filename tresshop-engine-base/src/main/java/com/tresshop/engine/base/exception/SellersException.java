package com.tresshop.engine.base.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class SellersException  {


    @AllArgsConstructor
    @Getter
    public static class SellerNotFoundException extends RuntimeException {
        private HttpStatus status;
        private String errorMsg;
    }
    @AllArgsConstructor
    @Getter
    public static class SellerBlockedException extends RuntimeException {
        private HttpStatus status;
        private String errorMsg;
    }
    @AllArgsConstructor
    @Getter
    public static class SellerException extends RuntimeException {
        private HttpStatus status;
        private String errorMsg;
    }
    @AllArgsConstructor
    @Getter
    public static class SellerAlreadyRegisteredException extends RuntimeException{
        private HttpStatus status;
        private String errorMsg;
    }
}
