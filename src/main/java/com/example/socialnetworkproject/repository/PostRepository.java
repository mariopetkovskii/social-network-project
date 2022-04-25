package com.example.socialnetworkproject.repository;

import com.example.socialnetworkproject.model.Post;
import com.example.socialnetworkproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "SELECT p FROM Post p WHERE p.user in :list")
    List<Post> findAllByUser(@Param("list") List<User> users);


}
