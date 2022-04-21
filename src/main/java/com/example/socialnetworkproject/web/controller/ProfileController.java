package com.example.socialnetworkproject.web.controller;


import com.example.socialnetworkproject.model.User;
import com.example.socialnetworkproject.model.exceptions.*;
import com.example.socialnetworkproject.service.ProfileService;
import com.example.socialnetworkproject.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;
    private final ProfileService profileService;

    public ProfileController(UserService userService, ProfileService profileService) {
        this.userService = userService;
        this.profileService = profileService;
    }

    @GetMapping
    public String getProfilePage(@RequestParam(required = false) String error, Model model, Authentication authentication) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        User user = (User) authentication.getPrincipal();
        model.addAttribute("profile", user);
        model.addAttribute("bodyContent", "profile");
        return "master-template";
    }

    @PostMapping("/editInfo")
    public String editInfo(@RequestParam String name,
                           @RequestParam String surname,
                           @RequestParam(required = false) String bio,
                           @RequestParam(required = false) String url,
                           @RequestParam String city,
                           Authentication authentication,
                           HttpServletRequest request){
        User user = (User) authentication.getPrincipal();
        this.profileService.editInfo(user.getUsername(), name, surname, bio, url, city);
        request.getSession().invalidate();
        return "redirect:/login?success=" + "Your changes has been updated, please login!";
    }

    @PostMapping("/editUsername")
    public String editUsername(@RequestParam String newUsername,
                               Authentication authentication,
                               HttpServletRequest request){
        try{
            User user = (User) authentication.getPrincipal();
            this.profileService.editUsername(user.getUsername(),newUsername);
            request.getSession().invalidate();
            return "redirect:/login?success=" + "Your username has been updated, please login!";
        } catch (UsernameAlreadyExistsException | InvalidUsernameException exception){
            return "redirect:/profile?error=" + exception.getMessage();
        }
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestParam String oldPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String repeatedNewPassword,
                                 Authentication authentication,
                                 HttpServletRequest request){
        try{
            User user = (User) authentication.getPrincipal();
            this.profileService.changePassword(user.getUsername(), oldPassword, newPassword, repeatedNewPassword);
            request.getSession().invalidate();
            return "redirect:/login?success=" + "Your password has been updated, please login!";
        } catch (PasswordsDoNotMatchException | OldPasswordException exception){
            return "redirect:/profile?error=" + exception.getMessage();
        }
    }

    @GetMapping("/{username}")
    public String getUserAccount(@RequestParam(required = false) String error, @PathVariable String username, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        try{
            User user = this.profileService.getUser(username);
            model.addAttribute("userAccountInfo", user);
            model.addAttribute("bodyContent", "userAccount");
            return "master-template";
        } catch (UserNotFound userNotFound){
            return "redirect:/home?error=" + userNotFound.getMessage();
        }

    }

}
