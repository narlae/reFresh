package com.onlyfresh.devkurly.web.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResult> SignInExHandler(HttpServletResponse response, HttpServletRequest request, SignInException e) throws IOException {
        log.error("[exceptionHandler] ex", e);
        ErrorResult errorResult = new ErrorResult("SignIn-EX", e.getMessage());
        String requestURI = request.getRequestURI();
        response.sendRedirect("/login?toURL=" + requestURI);
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }
}
