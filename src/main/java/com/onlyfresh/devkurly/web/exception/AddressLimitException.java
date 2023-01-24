package com.onlyfresh.devkurly.web.exception;

public class AddressLimitException extends RuntimeException{
    public AddressLimitException(String message) {
        super(message);
    }
}
