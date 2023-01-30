package com.onlyfresh.devkurly.web.exception;

public class OrderErrorException extends RuntimeException{
    public OrderErrorException(String message) {
        super(message);
    }
}
