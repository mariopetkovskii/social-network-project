package com.example.socialnetworkproject.service;

import com.example.socialnetworkproject.model.Post;
import com.example.socialnetworkproject.model.User;

import java.util.List;

public interface FollowersPostService {

    List<Post> findAllPostByUsername(String username);

}
