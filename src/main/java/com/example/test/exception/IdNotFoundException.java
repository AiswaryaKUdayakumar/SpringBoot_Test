package com.example.test.exception;

public class IdNotFoundException extends Exception {
    public IdNotFoundException(String noId) {
        super(noId);
    }
}
