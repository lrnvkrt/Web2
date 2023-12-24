package com.example.Web2.controllers;

import com.example.Web2.services.BrandService;
import com.example.Web2.services.OfferService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class HomePageController {

    private BrandService brandService;

    private OfferService offerService;

    private static final Logger LOG = LogManager.getLogger(Controller.class);

    @Autowired
    public void setBrandService(BrandService brandService) {
        this.brandService = brandService;
    }

    @Autowired
    public void setOfferService(OfferService offerService) { this.offerService = offerService; }

    @GetMapping("/home")
    public String getHomePage(Model model, Principal principal) {
        if(principal == null) {
            LOG.log(Level.INFO, "Go to home page");
        } else {
            LOG.log(Level.INFO, "Go to home page for " + principal.getName());
        }
        model.addAttribute("brands", brandService.findAllBrands());
        model.addAttribute("newOfferCards", offerService.findNewOffers());
        model.addAttribute("topOffers", offerService.getTopOffers(5));
        return "home-page";
    }


}
