package com.example.socialnetworkproject.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;

    @ManyToOne
    private User user;

    public Comment() {
    }

    public Comment(User user, String comment) {
        this.user = user;
        this.comment = comment;
    }
}
