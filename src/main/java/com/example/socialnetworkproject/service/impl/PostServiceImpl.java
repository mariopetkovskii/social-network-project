package com.example.socialnetworkproject.service.impl;


import com.example.socialnetworkproject.model.Comment;
import com.example.socialnetworkproject.model.Post;
import com.example.socialnetworkproject.model.exceptions.PostNotFoundException;
import com.example.socialnetworkproject.repository.CommentRepository;
import com.example.socialnetworkproject.repository.PostRepository;
import com.example.socialnetworkproject.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public PostServiceImpl(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Post> findAll() {
        return this.postRepository.findAll();
    }

    @Override
    public Optional<Post> save(String description) {
        return Optional.of(this.postRepository.save(new Post(description)));
    }

    @Override
    public Optional<Post> edit(Long id, String description) {
        Post post = this.postRepository.findById(id).orElseThrow(()-> new PostNotFoundException(id));
        post.setDescription(description);
        return Optional.of(this.postRepository.save(post));
    }

    @Override
    public Optional<Post> findById(Long id) {
        return this.postRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        this.postRepository.deleteById(id);
    }

    @Override
    public Optional<Post> like(Long id) {
        Post post = this.postRepository.findById(id).orElseThrow(()-> new PostNotFoundException(id));
        Integer likes = post.getLikes();
        likes = likes + 1;
        post.setLikes(likes);
        return Optional.of(this.postRepository.save(post));
    }

    @Override
    public Post saveComment(Long id, String comment) {
        Post post = this.postRepository.findById(id).orElseThrow(()-> new PostNotFoundException(id));
        Comment comment1 = this.commentRepository.save(new Comment(comment));
        post.getComments().add(comment1);
        return this.postRepository.save(post);
    }


    @Override
    public Optional<Comment> editComment(Long id, String comment) {
        Comment comment1 = this.commentRepository.findById(id).orElseThrow(()-> new PostNotFoundException(id));
        comment1.setComment(comment);
        return Optional.of(this.commentRepository.save(comment1));
    }

    @Override
    public Optional<Comment> findCommentById(Long id) {
        return this.commentRepository.findById(id);
    }

    @Override
    public void deleteCommentById(Long id) {
        this.commentRepository.deleteById(id);
    }
}
