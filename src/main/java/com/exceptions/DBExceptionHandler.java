package com.exceptions;

import java.sql.SQLException;

public class DBExceptionHandler{
    public RuntimeException Handle(SQLException e){
        if("23505".equals(e.getSQLState())){
            return new DuplicateEntryException("Duplicate entry: " + e.getMessage());
        }
        else if("08001".equals(e.getSQLState())){
            return new ConnectionFailedException("Failed to connect to the database: " + e.getMessage());
        }
        else if("23503".equals(e.getSQLState())){
            return new DBForeignKeyException("Foreign key already exists: " + e.getMessage());
        }
        else if("23502".equals(e.getSQLState())){
            return new NotNullException("Null value not allowed: " + e.getMessage());
        }
        else if("08003".equals(e.getSQLState())){
            return new ConnectionExistenceException("Connection does not exist: " + e.getMessage());
        }
        else
        return new RuntimeException("Database error: " + e.getMessage());
    }
}
