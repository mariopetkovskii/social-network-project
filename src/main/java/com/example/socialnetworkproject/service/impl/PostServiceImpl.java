package com.example.socialnetworkproject.service.impl;


import com.example.socialnetworkproject.model.Comment;
import com.example.socialnetworkproject.model.Post;
import com.example.socialnetworkproject.model.User;
import com.example.socialnetworkproject.model.exceptions.PostNotFoundException;
import com.example.socialnetworkproject.model.exceptions.UserNotFound;
import com.example.socialnetworkproject.repository.CommentRepository;
import com.example.socialnetworkproject.repository.PostRepository;
import com.example.socialnetworkproject.repository.UserRepository;
import com.example.socialnetworkproject.service.PostService;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public PostServiceImpl(PostRepository postRepository, CommentRepository commentRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Post> findAll() {
        return this.postRepository.findAll();
    }

    @Override
    public Optional<Post> save(String username, String description) {
        User user = this.userRepository.findByUsername(username).orElseThrow(()-> new UserNotFound("User not found!"));
        return Optional.of(this.postRepository.save(new Post(user, description)));
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
    public Optional<Post> like(Long id, String username) {
        Post post = this.postRepository.findById(id).orElseThrow(()-> new PostNotFoundException(id));
        User user = this.userRepository.findByUsername(username).orElseThrow(()-> new UserNotFound("User not found!"));
        List<User> likedBy = post.getLikedBy();
        if(!post.getLikedBy().contains(user)){
            likedBy.add(user);
            Integer likes = post.getLikes();
            likes = likes + 1;
            post.setLikes(likes);
        }
        else {
            likedBy.remove(user);
            Integer likes = post.getLikes();
            likes = likes - 1;
            post.setLikes(likes);
        }
        post.setLikedBy(likedBy);
        return Optional.of(this.postRepository.save(post));
    }



}
