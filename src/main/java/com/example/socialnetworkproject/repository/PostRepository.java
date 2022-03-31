package com.example.socialnetworkproject.repository;

import com.example.socialnetworkproject.model.Post;
import com.example.socialnetworkproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
