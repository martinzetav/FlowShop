package com.flowshop.common.exception;

public class InvalidStockOperationException extends RuntimeException{
    public InvalidStockOperationException(String message) {
        super(message);
    }
}
