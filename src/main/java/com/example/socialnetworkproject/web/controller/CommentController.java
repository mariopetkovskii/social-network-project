package com.example.socialnetworkproject.web.controller;

import com.example.socialnetworkproject.model.User;
import com.example.socialnetworkproject.service.CommentService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/add-comment/{id}")
    public String addComment(@PathVariable Long id, @RequestParam String comment, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        this.commentService.saveComment(id, user.getId(), comment);
        return "redirect:/posts";
    }
}
