package com.example.socialnetworkproject.service;

import com.example.socialnetworkproject.model.Comment;
import com.example.socialnetworkproject.model.Post;

import java.util.Optional;

public interface CommentService {
    Post saveComment(Long id, String username, String comment);
    Optional<Comment> editComment(Long id, String comment);
    Optional<Comment> findCommentById(Long id);
    void deleteCommentById(Long id);
}
