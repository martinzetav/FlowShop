package com.flowshop.common.exception;

public class UserServiceUnavailableException extends RuntimeException{
    public UserServiceUnavailableException(String message) {
        super(message);
    }
}
