package com.example.socialnetworkproject.service.impl;

import com.example.socialnetworkproject.model.City;
import com.example.socialnetworkproject.model.User;
import com.example.socialnetworkproject.model.exceptions.*;
import com.example.socialnetworkproject.repository.CityRepository;
import com.example.socialnetworkproject.repository.UserRepository;
import com.example.socialnetworkproject.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CityRepository cityRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, CityRepository cityRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.cityRepository = cityRepository;
    }

    @Override
    public User register(String username, String password, String repeatPassword, String name, String surname, LocalDate localDate, String city) {
        if (username==null || username.isEmpty()  || password==null || password.isEmpty())
            throw new InvalidUsernameOrPasswordException();
        if (!password.equals(repeatPassword))
            throw new PasswordsDoNotMatchException();
        if(this.userRepository.findByUsername(username).isPresent())
            throw new UsernameAlreadyExistsException(username);
        if(this.cityRepository.findByName(city).isEmpty())
            this.cityRepository.save(new City(city));
        User user = new User(username, passwordEncoder.encode(password), name, surname, localDate, city);
        return userRepository.save(user);

    }

    @Override
    public User login(String username, String password) {
        if (username==null || username.isEmpty() || password==null || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }
        return userRepository.findByUsernameAndPassword(username, password).orElseThrow(InvalidUserCredentialsException::new);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException(username));
    }
}
