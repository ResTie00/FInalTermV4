package com.exceptions;

public class DBForeignKeyException extends RuntimeException {
    public DBForeignKeyException(String message) {
        super(message);
    }
}
