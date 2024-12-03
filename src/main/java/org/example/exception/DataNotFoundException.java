package org.example.exception;

public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String s) {
        super(s);
    }
}
