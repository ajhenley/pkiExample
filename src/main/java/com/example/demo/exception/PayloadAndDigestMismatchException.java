package com.example.demo.exception;

public class PayloadAndDigestMismatchException extends RuntimeException {

    public PayloadAndDigestMismatchException(String message) {
        super(message);
    }

    public PayloadAndDigestMismatchException(String message, Throwable ex) {
        super(message, ex);
    }
}
