package com.example.socialnetworkproject.model.exceptions;

public class InvalidUsernameException extends RuntimeException{
    public InvalidUsernameException() {
        super("Invalid username!");
    }
}
