package com.example.socialnetworkproject.web.controller;

import com.example.socialnetworkproject.model.Foo;
import com.example.socialnetworkproject.model.User;
import com.example.socialnetworkproject.model.exceptions.CityNotFoundException;
import com.example.socialnetworkproject.service.WeatherService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/weather")
public class WeatherController {

    @Value("${api.key}")
    private String apiKey;

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    String getHomePage(@RequestParam(required = false) String error, Model model, HttpServletRequest req, RestTemplate restTemplate, Authentication authentication) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        String search = (String) req.getSession().getAttribute("search");
        String resourceUrl;
        User user = (User) authentication.getPrincipal();
        if(search != null){
            resourceUrl = "http://api.weatherapi.com/v1/current.json?key=" + this.apiKey + "&q=" + search + "&aqi=yes";
        }else {
            resourceUrl = "http://api.weatherapi.com/v1/current.json?key=" + this.apiKey + "&q=" + user.getCity() + "&aqi=yes";
        }
        Foo foo = restTemplate
                .getForObject(resourceUrl, Foo.class);
        model.addAttribute("items", foo);
        model.addAttribute("bodyContent", "weather");
        return "master-template";
    }

    @PostMapping
    public String searchCityPage(Model model,
                                 RestTemplate restTemplate,
                                 @RequestParam String city) {
        try {
            weatherService.getInfo(city, restTemplate, model);
            model.addAttribute("bodyContent", "weather");
            return "master-template";
        } catch (CityNotFoundException cityNotFoundException) {
            return "redirect:/weather?error=" + cityNotFoundException.getMessage();
        }
    }

}
