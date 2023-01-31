package com.onlyfresh.devkurly.web.exception;

public class WrongAccessException extends RuntimeException{
    public WrongAccessException(String message) {
        super(message);
    }
}
