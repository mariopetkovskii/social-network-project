package com.example.socialnetworkproject.service;

import com.example.socialnetworkproject.model.Comment;
import com.example.socialnetworkproject.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {
    List<Post> findAll();
    Optional<Post> save(Long id, String description);
    Optional<Post> edit(Long id, String description);
    Optional<Post> findById(Long id);
    void deleteById(Long id);

    Optional<Post> like(Long id, Long userId);


}
