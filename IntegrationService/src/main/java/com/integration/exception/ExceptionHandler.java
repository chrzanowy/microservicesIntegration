package com.integration.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Kuba on 2017-05-12.
 */
@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(UserNotExistingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleCustomException(UserNotExistingException ex) {
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UserAlreadySubscribedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleCustomException(UserAlreadySubscribedException ex) {
    }
}
