package com.example.socialnetworkproject.service.impl;

import com.example.socialnetworkproject.model.Post;
import com.example.socialnetworkproject.model.User;
import com.example.socialnetworkproject.model.exceptions.UserNotFound;
import com.example.socialnetworkproject.repository.FollowersRepository;
import com.example.socialnetworkproject.repository.PostRepository;
import com.example.socialnetworkproject.repository.UserRepository;
import com.example.socialnetworkproject.service.FollowersPostService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FollowersPostServiceImpl implements FollowersPostService {

    private final FollowersRepository followersRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public FollowersPostServiceImpl(FollowersRepository followersRepository, PostRepository postRepository, UserRepository userRepository) {
        this.followersRepository = followersRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Post> findAllPostByUsername(String username) {
        User user = this.userRepository.
                findByUsername(username).orElseThrow(() -> new UserNotFound(username));
        List<User> users = this.followersRepository.findAllByFollowers(user);
        List<Post> posts = this.postRepository.findAllByUser(users);

        return posts;

    }
}
