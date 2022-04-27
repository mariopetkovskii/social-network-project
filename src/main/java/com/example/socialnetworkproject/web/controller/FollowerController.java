package com.example.socialnetworkproject.web.controller;


import com.example.socialnetworkproject.model.User;
import com.example.socialnetworkproject.service.FollowerService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class FollowerController {

    private final FollowerService followerService;

    public FollowerController(FollowerService followerService) {
        this.followerService = followerService;
    }

    @GetMapping("/followers")
    public String getFriends(@RequestParam(required = false) String error, Model model, Authentication authentication){
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        User user = (User) authentication.getPrincipal();
        List<User> followers = this.followerService.allFollowers(user.getUsername());
        List<User> followingUsers = this.followerService.allFollowingUsers(user.getUsername());
        model.addAttribute("followers", followers);
        model.addAttribute("followingUsers", followingUsers);
        model.addAttribute("bodyContent", "followers");
        return "master-template";
    }

    @PostMapping("/follow/{username}")
    public String addFriend(@PathVariable String username, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        this.followerService.followUser(user.getUsername(), username);
        return "redirect:/profile/" + username;
    }

}
