package com.example.socialnetworkproject.model;


import lombok.Data;
import lombok.Generated;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@Entity
@Table(name="sn_users")
public class User implements UserDetails {
    @Id
    private String username;

    private String password;

    private String name;

    private String surname;

    private LocalDate dateOfBirth;

    private String city;

    private Role role;

    private String bio;

    private String url;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Post> postList;

    @ManyToMany
    private List<Post> postsLiked;

    @ManyToMany
    private List<User> followers;

    @ManyToMany
    private List<User> followingUsers;


    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialsNonExpired = true;
    private boolean isEnabled = true;


    public User() {
    }

    public User(String username, String password, String name, String surname, LocalDate dateOfBirth, String city) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.role = Role.ROLE_USER;
        this.city = city;
        this.bio = "";
        this.url = "";
        this.postList = new ArrayList<>();
    }

    public String getFullName(){
        return name + " " + surname;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(role);
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
