package com.example.socialnetworkproject.service;

import com.example.socialnetworkproject.model.User;

import java.util.Optional;

public interface ProfileService {

    Optional<User> editInfo(Long id, String name, String surname, String bio, String url, String city);

    Optional<User> editUsername(Long id, String username);

    Optional<User> changePassword(Long id, String oldPassword, String newPassword, String repeatedNewPassword);



}
