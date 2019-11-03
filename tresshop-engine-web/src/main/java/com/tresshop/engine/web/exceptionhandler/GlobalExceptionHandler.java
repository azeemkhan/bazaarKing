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

}
