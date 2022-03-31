package com.example.socialnetworkproject.model.exceptions;

public class OldPasswordException extends RuntimeException{
    public OldPasswordException() {
        super("The old password is incorrect!");
    }
}
