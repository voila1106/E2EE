package com.voila.e2eechatserver;

import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(HandlerMethodValidationException ex) {
        Map<String, String> errors = new HashMap<>();
        StringBuilder sb=new StringBuilder();
        for(ParameterValidationResult validationResult : ex.getAllValidationResults()){
            for(MessageSourceResolvable error : validationResult.getResolvableErrors()){
                sb.append(error.getDefaultMessage());
            }
        }
        errors.put("status", "400");
        errors.put("err", sb.toString());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
