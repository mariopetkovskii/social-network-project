package com.example.socialnetworkproject.web.controller;

import com.example.socialnetworkproject.model.User;
import com.example.socialnetworkproject.model.exceptions.InvalidArgumentsException;
import com.example.socialnetworkproject.model.exceptions.InvalidUserCredentialsException;
import com.example.socialnetworkproject.model.exceptions.PasswordsDoNotMatchException;
import com.example.socialnetworkproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String getLoginPage(Model model, @RequestParam(required = false) String success){
        if (success != null && !success.isEmpty()) {
            model.addAttribute("isSuccess", true);
            model.addAttribute("success", success);
        }
        model.addAttribute("bodyContent", "login");
        return "master-template";
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        User user = null;
        try{
            user = this.userService.login(request.getParameter("username"),
                    request.getParameter("password"));
            request.getSession().setAttribute("user", user);
            return "redirect:/home";
        }
        catch (InvalidUserCredentialsException exception) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", exception.getMessage());
            return "login";
        }
    }

    @GetMapping("/register")
    public String getRegisterPage(@RequestParam(required = false) String error, Model model) {
        if(error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        model.addAttribute("bodyContent","register");
        return "master-template";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String repeatedPassword,
                           @RequestParam String name,
                           @RequestParam String surname,
                           @RequestParam String localDate,
                           @RequestParam String city) {
        try{
            LocalDate localDateParsed = LocalDate.parse(localDate);
            this.userService.register(username, password, repeatedPassword, name, surname, localDateParsed, city);
            return "redirect:/login";
        } catch (InvalidArgumentsException | PasswordsDoNotMatchException exception) {
            return "redirect:/register?error=" + exception.getMessage();
        }
    }

}


