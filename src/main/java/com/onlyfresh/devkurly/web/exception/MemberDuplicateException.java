package com.onlyfresh.devkurly.web.exception;

public class MemberDuplicateException extends RuntimeException{
    public MemberDuplicateException(String message) {
        super(message);
    }
}
