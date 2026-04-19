package com.exceptions;

public class ConnectionExistenceException extends RuntimeException {
    public ConnectionExistenceException(String message) {
        super(message);
    }
}
