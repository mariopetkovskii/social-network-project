package com.example.socialnetworkproject.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private Integer likes;

    @OneToMany
    public List<Comment> comments;

    public Post() {
    }

    public Post(String description) {
        this.description = description;
        this.likes = 0;
        this.comments = new ArrayList<>();
    }
}
