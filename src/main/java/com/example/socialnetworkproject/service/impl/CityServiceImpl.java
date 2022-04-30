package com.example.socialnetworkproject.service.impl;

import antlr.StringUtils;
import com.example.socialnetworkproject.model.City;
import com.example.socialnetworkproject.model.User;
import com.example.socialnetworkproject.model.exceptions.CityNotFoundException;
import com.example.socialnetworkproject.repository.CityRepository;
import com.example.socialnetworkproject.repository.UserRepository;
import com.example.socialnetworkproject.service.CityService;
import io.netty.util.internal.StringUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final UserRepository userRepository;

    public CityServiceImpl(CityRepository cityRepository, UserRepository userRepository) {
        this.cityRepository = cityRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<City> findALl() {
        return this.cityRepository.findAll();
    }

    @Override
    public City findByName(String name) {
        return this.cityRepository.findByName(name).orElseThrow(CityNotFoundException::new);
    }

    @Override
    public List<User> usersFromThisCity(String city) {
        String capitalize = city.substring(0,1).toUpperCase() + city.substring(1).toLowerCase();
        if(this.cityRepository.findByName(capitalize).isEmpty())
            throw new CityNotFoundException();
        List<User> users = this.userRepository.findAll();
        return users.stream()
                .filter(user -> city.equalsIgnoreCase(user.getCity()))
                .collect(Collectors.toList());
    }
}
