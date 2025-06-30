package com.microservice.cart.exception;

public class UserAlreadyHasActiveCartException extends RuntimeException{
    public UserAlreadyHasActiveCartException(String message) {
        super(message);
    }
}
