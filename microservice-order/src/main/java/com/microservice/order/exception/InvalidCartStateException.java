package com.microservice.order.exception;

public class InvalidCartStateException extends RuntimeException{
    public InvalidCartStateException(String message) {
        super(message);
    }
}
