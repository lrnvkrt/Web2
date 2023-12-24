package com.example.Web2.controllers;

import com.example.Web2.dtos.BrandDto;
import com.example.Web2.dtos.ModelDto;
import com.example.Web2.models.UserRole;
import com.example.Web2.services.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private BrandService<UUID> brandService;

    private ModelService<UUID> modelService;

    private OfferService<UUID> offerService;

    private UserService<UUID> userService;

    private UserRoleService<UUID> roleService;

    @Autowired
    public void setBrandService(BrandService<UUID> brandService) {
        this.brandService = brandService;
    }

    @Autowired
    public void setModelService(ModelService<UUID> modelService) {
        this.modelService = modelService;
    }

    @Autowired
    public void setOfferService(OfferService<UUID> offerService) {
        this.offerService = offerService;
    }

    @Autowired
    public void setUserService(UserService<UUID> userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(UserRoleService<UUID> roleService) {
        this.roleService = roleService;
    }

    @GetMapping()
    public String getAdminPanel() {
        return "admin/admin-panel";
    }

    @GetMapping("/brands")
    public String getAdminBrands(Model model) {
        model.addAttribute("brands", brandService.findAllBrands());
        model.addAttribute("newBrand", new BrandDto());
        return "admin/admin-brands";
    }

    @PostMapping("/brands/add")
    public String doAddBrand(@ModelAttribute("newBrand") @Valid BrandDto brandDto,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("brands", brandService.findAllBrands());
            model.addAttribute("newBrand", brandDto);
            return "admin/admin-brands";
        }
        brandService.addBrand(brandDto);
        return "redirect:/admin/brands";
    }

    @GetMapping("/models")
    public String getAdminModels(Model model) {
        model.addAttribute("brands", brandService.findAllBrands());
        model.addAttribute("models", modelService.findAllModels());
        model.addAttribute("newModel", new ModelDto());
        return "admin/admin-models";
    }

    @PostMapping("/models/add")
    public String doAddBrand(@ModelAttribute("newModel") @Valid ModelDto modelDto,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("brands", brandService.findAllBrands());
            model.addAttribute("models", modelService.findAllModels());
            model.addAttribute("newModel", modelDto);
            System.out.println(bindingResult.getAllErrors());
            System.out.println(modelDto);
            return "admin/admin-models";
        }
        modelService.addModel(modelDto);
        return "redirect:/admin/models";
    }

    @GetMapping("/offers")
    public String getAdminOffers(Model model) {
        model.addAttribute("offers", offerService.findAllOffers());
        return "admin/admin-offers";
    }

    @PostMapping("/offers/delete")
    public String doDeleteOffer(@RequestParam("offerId") UUID offerId) {
        offerService.deleteOffer(offerId);
        return "redirect:/admin/offers";
    }

    @GetMapping("/users")
    public String getAdminUsers(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "admin/admin-users";
    }

    @PostMapping("/users/toggle_active")
    public String doToggleActive(@RequestParam("username") String username) {
        userService.changeActivation(username);
        return "redirect:/admin/users";
    }

    @PostMapping("/users/change_role")
    public String doChangeRole(@RequestParam("username") String username,
                               @RequestParam("newRole")UserRole.Role newRole) {
        userService.changeRole(username, newRole);
        return "redirect:/admin/users";
    }

}
