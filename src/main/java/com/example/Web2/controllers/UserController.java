package com.example.Web2.controllers;

import com.example.Web2.dtos.UserDto;
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

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "all-users";
    }

    @GetMapping("/adduser")
    public String getFormAddUser(Model model) {
        model.addAttribute("new-user", new UserDto());
        return "user-form";
    }

    @PostMapping("/adduser")
    public String addUser(@Validated UserDto userDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user-form";
        }

        userService.addUser(userDto);
        return "redirect:/all";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") UUID id, @Validated UserDto userDto,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "update-user";
        }

        userService.updateUser(userDto);
        return "redirect:/index";
    }

    @PostMapping("/delete/{username}")
    public String deleteUser(@PathVariable("id") String username, Model model) {
        userService.changeActivation(username);
        return "redirect:/index";
    }
}