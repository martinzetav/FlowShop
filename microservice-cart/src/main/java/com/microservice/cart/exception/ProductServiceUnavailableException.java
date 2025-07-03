package com.microservice.cart.exception;

public class ProductServiceUnavailableException extends RuntimeException{
    public ProductServiceUnavailableException(String message) {
        super(message);
    }
}
