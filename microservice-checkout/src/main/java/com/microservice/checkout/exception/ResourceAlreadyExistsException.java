package com.microservice.checkout.exception;

public class ResourceAlreadyExistsException extends Exception{
    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}
