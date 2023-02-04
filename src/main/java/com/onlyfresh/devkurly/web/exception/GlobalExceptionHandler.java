package com.onlyfresh.devkurly.web.exception;

import com.onlyfresh.devkurly.web.dto.member.LoginFormDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
    public ResponseEntity<ErrorResult> alreadyPush(LikeNoException e ) {
        return getResponseEntity("LikeNo-Ex", e);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> memberListExHandler(MemberListException e) {
        return getResponseEntity("Mem-Ex", e);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> AddressLimitExHandler(HttpServletResponse response, AddressLimitException e) throws IOException {
        response.sendRedirect("/address/list");
        return getResponseEntity("Addr-Ex", e);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> memberDuplicateExHandler(HttpServletResponse response, MemberDuplicateException e) throws IOException {
        response.sendRedirect("/register?error=1");
        return getResponseEntity("Mem-Ex", e);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> orderExHandler(HttpServletResponse response, OrderErrorException e) throws IOException {
        response.sendRedirect("/cart?error=1");
        return getResponseEntity("SignIn-EX", e);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> orderExHandler(HttpServletResponse response, WrongAccessException e) throws IOException {
        response.sendRedirect("/order/list?error=1");
        return getResponseEntity("SignIn-EX", e);
    }


    private ResponseEntity<ErrorResult> getResponseEntity(String code, Exception e) {
        ErrorResult errorResult = new ErrorResult(code, e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    private ErrorResult makeErrorResult(BindingResult bindingResult, String requestURI) {
        String code = "";
        String message = "";
        String detail = "";
        String path = "";

        if (bindingResult.hasErrors()) {
            detail = bindingResult.getFieldError().getDefaultMessage();
            String bindingResultCode = bindingResult.getFieldError().getCode();

            switch (bindingResultCode) {
                case "NotEmpty":
                    code = ErrorCode.NOT_EMPTY.getCode();
                    message = ErrorCode.NOT_EMPTY.getMessage();
                    path = requestURI;
                    break;
            }
        }
        return new ErrorResult(code, message, detail, path);

    }
}
