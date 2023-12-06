package com.example.Web2.controllers;

import com.example.Web2.services.BrandService;
import com.example.Web2.services.impl.BrandServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomePageController {

    private BrandService brandService;

    @Autowired
    public void setBrandService(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("/home")
    public String getHomePage(Model model) {
        model.addAttribute("brands", brandService.findAllBrands());
        return "home-page";
    }
}
