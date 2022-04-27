package com.example.socialnetworkproject.service.impl;


import com.example.socialnetworkproject.errorhandler.RestTemplateResponseErrorHandler;
import com.example.socialnetworkproject.model.Foo;
import com.example.socialnetworkproject.model.exceptions.CityNotFoundException;
import com.example.socialnetworkproject.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

@ContextConfiguration(classes = { CityNotFoundException.class, Foo.class })
@Service
public class WeatherServiceImpl implements WeatherService {

    @Value("${api.key}")
    private String apiKey;

    @Autowired
    private RestTemplateBuilder builder;

    @Autowired
    public WeatherServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        restTemplateBuilder
                .errorHandler(new RestTemplateResponseErrorHandler())
                .build();
    }


    @Override()
    public void getInfo(String city, RestTemplate restTemplate, Model model) {
        String resourceUrl = "http://api.weatherapi.com/v1/current.json?key=" + this.apiKey +"&q=" + city + "&aqi=yes";

        restTemplate = this.builder
                .errorHandler(new RestTemplateResponseErrorHandler())
                .build();

        Foo response = restTemplate
                .getForObject(resourceUrl, Foo.class);

        model.addAttribute("items", response);
    }
}
