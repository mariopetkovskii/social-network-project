package com.example.socialnetworkproject.service;

import com.example.socialnetworkproject.model.User;

import java.util.List;
import java.util.Optional;

public interface FollowerService {

    List<User> allFollowers(String username);

    List<User> allFollowingUsers(String username);

    Optional<User> followUser(String username, String newFriendUsername);

    void unfollowUser(String username, String friendUsername);

}
