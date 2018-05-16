package com.example.demo.exception;

public class EncryptionException extends RuntimeException {

    public EncryptionException(String message, Throwable ex) {
        super(message, ex);
    }
}