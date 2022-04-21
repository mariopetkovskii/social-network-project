package com.example.socialnetworkproject.service;

import com.example.socialnetworkproject.model.User;

import java.util.Optional;

public interface ProfileService {

    Optional<User> editInfo(String username, String name, String surname, String bio, String url, String city);

    Optional<User> editUsername(String username, String newUsername);

    Optional<User> changePassword(String username, String oldPassword, String newPassword, String repeatedNewPassword);

    User getUser(String username);


}
