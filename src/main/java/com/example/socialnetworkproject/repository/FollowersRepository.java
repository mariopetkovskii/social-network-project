package com.example.socialnetworkproject.repository;

import com.example.socialnetworkproject.model.Post;
import com.example.socialnetworkproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowersRepository extends JpaRepository<User, String> {
    List<User> findAllByFollowingUsers(User user);
    List<User> findAllByFollowers(User user);
}
