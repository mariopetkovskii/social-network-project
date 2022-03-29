package com.example.socialnetworkproject.repository;

import com.example.socialnetworkproject.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
