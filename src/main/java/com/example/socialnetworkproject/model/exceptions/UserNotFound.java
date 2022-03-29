package com.example.socialnetworkproject.model.exceptions;

public class UserNotFound extends RuntimeException{
    public UserNotFound(String message) {
        super(message);
    }
}
