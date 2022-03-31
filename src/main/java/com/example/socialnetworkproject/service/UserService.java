package com.example.socialnetworkproject.service;


import com.example.socialnetworkproject.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDate;

public interface UserService extends UserDetailsService {
    User register(String username, String password, String repeatPassword, String name, String surname, LocalDate localDate, String city);
    User login(String username, String password);
}
