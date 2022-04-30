package com.example.socialnetworkproject.service;

import com.example.socialnetworkproject.model.City;
import com.example.socialnetworkproject.model.User;

import java.util.List;

public interface CityService {

    List<City> findALl();

    City findByName(String name);

    List<User> usersFromThisCity(String city);

}
