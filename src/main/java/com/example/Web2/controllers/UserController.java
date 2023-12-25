package com.example.Web2.controllers;

import com.example.Web2.dtos.PasswordDto;
import com.example.Web2.dtos.UserDto;
import com.example.Web2.dtos.UserRegistrationDto;
import com.example.Web2.dtos.UsernameDto;
import com.example.Web2.services.OfferService;
import com.example.Web2.services.UserService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    private OfferService offerService;

    private static final Logger LOG = LogManager.getLogger(Controller.class);

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setOfferService(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping("/details/{username}")
    public String getUser(@PathVariable("username") String username, Model model, Principal principal) {
        if (principal == null) {
            LOG.log(Level.INFO, "Show info about user " + username);
        } else {
            LOG.log(Level.INFO, "Show info about user " + username + " for user " + principal.getName());
        }
        model.addAttribute("user", userService.findByUsername(username));
        model.addAttribute("offers", offerService.findCardsByUsername(username));
        System.out.println(offerService.findCardsByUsername(username).size());
        return "user-details";
    }

    @GetMapping("/account")
    public String getAccount(Model model, Principal principal) {
        UserDto userDto = userService.findUserDtoByUsername(principal.getName());
        model.addAttribute("user", userDto);
        return "account";
    }

    @ModelAttribute("usernameDto")
    public UsernameDto initUsernameForm() {
        return new UsernameDto();
    }

    @ModelAttribute("userDto")
    public UserDto initUserForm(Principal principal) {
        if (principal == null) {
            return new UserDto();
        }
        return userService.findUserDtoByUsername(principal.getName());
    }

    @ModelAttribute("passwordDto")
    public PasswordDto initPasswordForm() {
        return new PasswordDto();
    }

    @GetMapping("/edit")
    public String getEdit() {
        return "user-edit";
    }

    @PostMapping("/edit")
    public String doEdit(@Valid UserDto userDto,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userDto", userDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userDto", bindingResult);
            System.out.println("Что-то пошло не так! " + userDto);
            System.out.println(bindingResult.getAllErrors());
            return "redirect:/user/edit";
        }

        this.userService.updateUser(userDto);

        return "redirect:/user/account";

    }

    @GetMapping("/edit/username")
    public String getEditUsername() {
        return "username-edit";
    }

    @PostMapping("/edit/username")
    public String doEditUsername(@Valid UsernameDto usernameDto,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 Principal principal) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("usernameDto", usernameDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.usernameDto", bindingResult);
            return "redirect:/user/edit/username";
        }

        this.userService.changeUsername(principal.getName(), usernameDto.getUsername());

        return "redirect:/logout";
    }

    @GetMapping("/edit/password")
    public String getEditPassword() {
        return "password-edit";
    }

    @PostMapping("/edit/password")
    public String doEditPassword(@Valid PasswordDto passwordDto,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 Principal principal) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("passwordDto", passwordDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.passwordDto", bindingResult);
            return "redirect:/user/edit/password";
        }

        boolean isCorrect = this.userService.changePassword(principal.getName(), passwordDto.getOldPassword(), passwordDto.getNewPassword());

        if(!isCorrect) {
            bindingResult.rejectValue("oldPassword", "error.password", "Старый пароль неправильный!");
            redirectAttributes.addFlashAttribute("passwordDto", passwordDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.passwordDto", bindingResult);
            return "redirect:/user/edit/password";
        }

        return "redirect:/logout";
    }

    @GetMapping("/my_offers")
    public String getUserOffers(Model model, Principal principal) {
        model.addAttribute("offers", offerService.findCardsByUsername(principal.getName()));
        return "user-offers";
    }

    @ModelAttribute("registrationDto")
    public UserRegistrationDto initForm() {
        return new UserRegistrationDto();
    }

    @GetMapping("/registration")
    public String register() { return "user-add"; }

    @PostMapping("/registration")
    public String doRegister(@Valid UserRegistrationDto registrationDto,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("registrationDto", registrationDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registrationDto", bindingResult);

            return "redirect:/user/registration";
        }

        this.userService.register(registrationDto);

        return "redirect:/login";
    }

}