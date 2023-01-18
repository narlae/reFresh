package com.onlyfresh.devkurly.web.exception;

import lombok.Getter;

public enum ErrorCode {

    NOT_EMPTY("ERROR_CODE_0001", "필수값이 누락되었습니다."),
    INVALID_LOGIN("ERROR_CODE_0002", "아이디 또는 비밀번호가 틀렸습니다.")
    ;


    @Getter
    private final String code;
    @Getter
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
