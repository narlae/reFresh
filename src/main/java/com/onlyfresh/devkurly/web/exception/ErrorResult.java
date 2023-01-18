package com.onlyfresh.devkurly.web.exception;


import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ErrorResult {

    private Timestamp timestamp;
    private String code;
    private String message;
    private String detail;

    private String path;

    public ErrorResult(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorResult(String code, String message, String detail, String path) {
        this.code = code;
        this.message = message;
        this.detail = detail;
        this.path = path;
    }
}
