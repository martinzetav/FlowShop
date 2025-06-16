package com.microservice.checkout.exception;

public class ResourceNotFoundException extends Exception{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
