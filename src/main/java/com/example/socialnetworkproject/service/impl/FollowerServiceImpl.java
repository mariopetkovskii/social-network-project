package com.example.socialnetworkproject.service.impl;


import com.example.socialnetworkproject.model.User;
import com.example.socialnetworkproject.model.exceptions.UserNotFound;
import com.example.socialnetworkproject.repository.UserRepository;
import com.example.socialnetworkproject.service.FollowerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FollowerServiceImpl implements FollowerService {

    private final UserRepository userRepository;

    public FollowerServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> allFollowers(String username) {
        User user = this.userRepository.findByUsername(username).orElseThrow(()-> new UserNotFound("User not found!"));
        return user.getFollowers();
    }

    @Override
    public List<User> allFollowingUsers(String username) {
        User user = this.userRepository.findByUsername(username).orElseThrow(()-> new UserNotFound("User not found!"));
        return user.getFollowingUsers();
    }

    @Override
    public Optional<User> followUser(String username, String newFollowerUsername) {
        User user = this.userRepository.findByUsername(username).orElseThrow(()-> new UserNotFound("User not found!"));
        User friend = this.userRepository.findByUsername(newFollowerUsername).orElseThrow(()-> new UserNotFound("User not found!"));
        user.getFollowingUsers().add(friend);
        friend.getFollowers().add(user);
        this.userRepository.save(friend);
        return Optional.of(this.userRepository.save(user));
    }

    @Override
    public void unfollowUser(String username, String followerUsername) {
        User user = this.userRepository.findByUsername(username).orElseThrow(()-> new UserNotFound("User not found!"));
        User friend = this.userRepository.findByUsername(followerUsername).orElseThrow(()-> new UserNotFound("User not found!"));
        user.getFollowingUsers().remove(friend);
        friend.getFollowers().remove(user);
        this.userRepository.save(friend);
        this.userRepository.save(user);
    }
}
