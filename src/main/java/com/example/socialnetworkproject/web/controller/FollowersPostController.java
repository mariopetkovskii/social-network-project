package com.example.socialnetworkproject.web.controller;


import com.example.socialnetworkproject.model.Post;
import com.example.socialnetworkproject.model.User;
import com.example.socialnetworkproject.service.FollowersPostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/postfollowers")
public class FollowersPostController {

    private final FollowersPostService followersPostService;

    public FollowersPostController(FollowersPostService followersPostService) {
        this.followersPostService = followersPostService;
    }

    @GetMapping
    public String getFollowersPostPage(Authentication authentication, Model model){
        User user = (User) authentication.getPrincipal();
        List<Post> postList = this.followersPostService.findAllPostByUsername(user.getUsername());
        model.addAttribute("followersPostList", postList);
        model.addAttribute("bodyContent", "followersPost");
        return "master-template";
    }
}
