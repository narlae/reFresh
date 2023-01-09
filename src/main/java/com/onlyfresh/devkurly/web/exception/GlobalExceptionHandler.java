package com.onlyfresh.devkurly.web.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResult> signInExHandler(HttpServletResponse response, HttpServletRequest request, SignInException e) throws IOException {
        String requestURI = request.getRequestURI();
        response.sendRedirect("/login?toURL=" + requestURI);
        return getResponseEntity("SignIn-EX", e);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> alreadyPush(LikeNoException e ) {
        return getResponseEntity("LikeNo-Ex", e);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> memberListExHandler(MemberListException e) {
        return getResponseEntity("Mem-Ex", e);
    }

    private ResponseEntity<ErrorResult> getResponseEntity(String code, Exception e) {
        ErrorResult errorResult = new ErrorResult(code, e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }
}
