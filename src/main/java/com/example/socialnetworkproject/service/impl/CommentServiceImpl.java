package com.example.socialnetworkproject.service.impl;

import com.example.socialnetworkproject.model.Comment;
import com.example.socialnetworkproject.model.Post;
import com.example.socialnetworkproject.model.User;
import com.example.socialnetworkproject.model.exceptions.PostNotFoundException;
import com.example.socialnetworkproject.model.exceptions.UserNotFound;
import com.example.socialnetworkproject.repository.CommentRepository;
import com.example.socialnetworkproject.repository.PostRepository;
import com.example.socialnetworkproject.repository.UserRepository;
import com.example.socialnetworkproject.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public CommentServiceImpl(PostRepository postRepository, CommentRepository commentRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Post saveComment(Long id, Long userId, String comment) {
        Post post = this.postRepository.findById(id).orElseThrow(()-> new PostNotFoundException(id));
        User user = this.userRepository.findById(userId).orElseThrow(()-> new UserNotFound("User not found!"));
        Comment comment1 = this.commentRepository.save(new Comment(user, comment));
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
