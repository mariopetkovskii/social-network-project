package com.example.socialnetworkproject.web.controller;

import com.example.socialnetworkproject.model.Post;
import com.example.socialnetworkproject.model.User;
import com.example.socialnetworkproject.model.exceptions.CityNotFoundException;
import com.example.socialnetworkproject.service.CityService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/nearby")
    public String getCityPage(@RequestParam(required = false) String error, Model model, Authentication authentication, HttpServletRequest req) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<User> usersNearby;
        String search = (String) req.getSession().getAttribute("search");
        if (search != null) {
            usersNearby = this.cityService.usersFromThisCity(search);
        }else {
            User user = (User) authentication.getPrincipal();
            usersNearby = this.cityService.usersFromThisCity(user.getCity());
        }
        model.addAttribute("nearbyUsers", usersNearby);
        model.addAttribute("bodyContent", "nearby");
        return "master-template";
    }

    @PostMapping("/search")
    public String searchFilter(HttpServletRequest req, Model model){
        String search = req.getParameter("search");
        try {
            this.cityService.usersFromThisCity(search);
        } catch (CityNotFoundException exception){
            return "redirect:/nearby?error=" + exception.getMessage();
        }
        req.getSession().setAttribute("search", search);
        return "redirect:/nearby";
    }

}
