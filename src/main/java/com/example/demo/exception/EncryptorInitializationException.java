package com.example.demo.exception;

public class EncryptorInitializationException extends RuntimeException {

    public EncryptorInitializationException(String message, Throwable ex) {
        super(message, ex);
    }
}