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
    public Optional<User> editInfo(Long id, String name, String surname, String bio, String url, String city) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new UserNotFound("User not found!"));
        user.setBio(bio);
        user.setName(name);
        user.setSurname(surname);
        user.setUrl(url);
        user.setCity(city);
        this.userRepository.save(user);
        return Optional.of(user);
    }

    @Override
    public Optional<User> editUsername(Long id, String username) {
        if (username==null || username.isEmpty())
            throw new InvalidUsernameOrPasswordException();
        if(this.userRepository.findByUsername(username).isPresent())
            throw new UsernameAlreadyExistsException(username);
        User user = this.userRepository.findById(id).orElseThrow(() -> new UserNotFound("User not found!"));
        user.setUsername(username);
        this.userRepository.save(user);
        return Optional.of(user);
    }

    @Override
    public Optional<User> changePassword(Long id, String oldPassword, String newPassword, String repeatedNewPassword) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new UserNotFound("User not found!"));
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
}
