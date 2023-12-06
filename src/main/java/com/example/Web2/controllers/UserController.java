package com.example.Web2.controllers;

import com.example.Web2.dtos.UserDto;
import com.example.Web2.services.OfferService;
import com.example.Web2.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    private OfferService offerService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setOfferService(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping("/details/{username}")
    public String getUser(@PathVariable("username") String username, Model model) {
        model.addAttribute("user", userService.findByUsername(username));
        model.addAttribute("offers", offerService.findCardsByUsername(username));
        System.out.println(offerService.findCardsByUsername(username).size());
        return "user-details";
    }
}