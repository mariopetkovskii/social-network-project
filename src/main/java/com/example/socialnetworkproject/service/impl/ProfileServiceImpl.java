package com.example.socialnetworkproject.service.impl;

import com.example.socialnetworkproject.model.User;
import com.example.socialnetworkproject.model.exceptions.*;
import com.example.socialnetworkproject.repository.UserRepository;
import com.example.socialnetworkproject.service.ProfileService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ProfileServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> editInfo(String username, String name, String surname, String bio, String url, String city) {
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new UserNotFound("User not found!"));
        user.setBio(bio);
        user.setName(name);
        user.setSurname(surname);
        user.setUrl(url);
        user.setCity(city);
        this.userRepository.save(user);
        return Optional.of(user);
    }

    @Override
    public Optional<User> editUsername(String username, String newUsername) {
        if (newUsername==null || newUsername.isEmpty())
            throw new InvalidUsernameOrPasswordException();
        if(this.userRepository.findByUsername(newUsername).isPresent())
            throw new UsernameAlreadyExistsException(newUsername);
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new UserNotFound("User not found!"));
        user.setUsername(newUsername);
        this.userRepository.save(user);
        return Optional.of(user);
    }

    @Override
    public Optional<User> changePassword(String username, String oldPassword, String newPassword, String repeatedNewPassword) {
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new UserNotFound("User not found!"));
        boolean isOldAndNewPasswordSame = this.passwordEncoder.matches(oldPassword, user.getPassword());
        if(isOldAndNewPasswordSame){
            if (!newPassword.equals(repeatedNewPassword))
                throw new PasswordsDoNotMatchException();
            else{
                user.setPassword(passwordEncoder.encode(newPassword));
                this.userRepository.save(user);
            }
        }
        else{
            throw new OldPasswordException();
        }
        return Optional.of(user);
    }

    @Override
    public User getUser(String username) {
        return this.userRepository.findByUsername(username).orElseThrow(() -> new UserNotFound("User not found!"));
    }
}
