package com.oskarro.muzikum.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


/**
 * Global Exception Handler which takes care of exception handling for all controlers
 * */

@ControllerAdvice
public class ApiExceptionHandler {


    // TODO
/*    @ExceptionHandler({ UnauthorizedException.class, TaskNotFoundException.class })
    public final ResponseEntity<ApiError> handleException(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        if (ex instanceof UnauthorizedException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            UnauthorizedException ue = (UnauthorizedException) ex;

            return handleUserNotFoundException(ue, headers, status, request);
        } else if (ex instanceof TaskNotFoundException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            TaskNotFoundException tnfe = (TaskNotFoundException) ex;

            return handleContentNotAllowedException(tnfe, headers, status, request);
        } else {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleExceptionInternal(ex, null, headers, status, request);
        }
    }*/
}
