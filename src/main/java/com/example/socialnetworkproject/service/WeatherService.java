package com.example.socialnetworkproject.service;

import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

public interface WeatherService {
    void getInfo(String city, RestTemplate restTemplate, Model model);
}
