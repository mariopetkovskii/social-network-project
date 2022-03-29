package com.example.socialnetworkproject.web.controller;


import com.example.socialnetworkproject.model.Post;
import com.example.socialnetworkproject.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public String getPostPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Post> posts = this.postService.findAll();
        model.addAttribute("posts", posts);
        model.addAttribute("bodyContent", "posts");
        return "master-template";
    }

    @DeleteMapping("/delete/{id}")
    public String deletePost(@PathVariable Long id) {
        this.postService.deleteById(id);
        return "redirect:/posts";
    }

    @GetMapping("/edit-form/{id}")
    public String editPostPage(@PathVariable Long id, Model model) {
        if (this.postService.findById(id).isPresent()) {
            Post post = this.postService.findById(id).get();
            model.addAttribute("post", post);
            model.addAttribute("bodyContent", "add-post");
            return "master-template";
        }
        return "redirect:/posts?error=PostNotFound";
    }

    @GetMapping("/add-form")
    public String addPostPage(Model model) {
        model.addAttribute("bodyContent", "add-post");
        return "master-template";
    }

    @PostMapping("/add")
    public String savePost(
            @RequestParam(required = false) Long id,
            @RequestParam String description) {
        if (id != null) {
            this.postService.edit(id, description);
        } else {
            this.postService.save(description);
        }
        return "redirect:/posts";
    }

    @PostMapping("/like/{id}")
    public String addLike(@PathVariable Long id){
        this.postService.like(id);
        return "redirect:/posts";
    }


    @PostMapping("/add-comment/{id}")
    public String addComment(@PathVariable Long id, @RequestParam String comment){
        this.postService.saveComment(id, comment);
        return "redirect:/posts";
    }

}
