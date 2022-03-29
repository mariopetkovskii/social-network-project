package com.example.socialnetworkproject.model.exceptions;

public class CommentNotFoundException extends RuntimeException{
    public CommentNotFoundException(Long id) {
        super(String.format("Comment with id %d is not found!", id));
    }
}
