package com.tresshop.engine.web.exceptionhandler;

import com.tresshop.engine.base.exception.ExceptionResponse;
import com.tresshop.engine.base.exception.RewardException;
import com.tresshop.engine.base.exception.RewardNotFoundException;
import com.tresshop.engine.base.exception.ShareAndReferException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({RewardException.class, ShareAndReferException.class, RewardNotFoundException.class})
    public final ResponseEntity<ExceptionResponse> handleInternalServerException(Exception ex, WebRequest request) {
        if (ex instanceof RewardException) {
            RewardException rewardException = (RewardException) ex;
            return handleRewardExceptionException(rewardException, HttpStatus.INTERNAL_SERVER_ERROR, request);
        }

        if (ex instanceof ShareAndReferException) {
            ShareAndReferException shareAndReferException = (ShareAndReferException) ex;
            return handleShareAndReferException(shareAndReferException, HttpStatus.INTERNAL_SERVER_ERROR, request);
        }

        if (ex instanceof RewardNotFoundException) {
            RewardNotFoundException rewardNotFoundException = (RewardNotFoundException) ex;
            return handleRewardNotFoundException(rewardNotFoundException, HttpStatus.BAD_REQUEST, request);
        }

        return handleGenericExceptionException(ex, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    protected ResponseEntity<ExceptionResponse> handleRewardExceptionException(
            RewardException ex, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(
                new ExceptionResponse(
                        ex.getStatusCode().value(),
                        ex.getErrorMsg()),
                ex.getStatusCode());
    }

    protected ResponseEntity<ExceptionResponse> handleGenericExceptionException(
            Exception ex, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(
                new ExceptionResponse(
                        status.value(),
                        "Something went wrong!"),
                status);
    }

    protected ResponseEntity<ExceptionResponse> handleRewardNotFoundException(
            RewardNotFoundException ex, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(
                new ExceptionResponse(
                        ex.getStatusCode().value(),
                        ex.getErrorMsg()),
                ex.getStatusCode());
    }

    protected ResponseEntity<ExceptionResponse> handleShareAndReferException(
            ShareAndReferException ex, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(
                new ExceptionResponse(
                        ex.getStatus().value(),
                        ex.getErrorMsg()),
                ex.getStatus());
    }
}
