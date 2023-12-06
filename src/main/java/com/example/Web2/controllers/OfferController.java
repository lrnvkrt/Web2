package com.example.Web2.controllers;

import com.example.Web2.services.ModelService;
import com.example.Web2.services.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/offers")
public class OfferController {

    private OfferService offerService;

    private ModelService modelService;

    @Autowired
    public void setOfferService(OfferService offerService) {
        this.offerService = offerService;
    }
    @Autowired
    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }

    @GetMapping("/all")
    public String getAllOffers(Model model) {
        model.addAttribute("offerCards", offerService.findAllCards());
        return "offers";
    }

    @GetMapping("/{name}")
    public String getOffersByBrand(@PathVariable("name") String brandName, Model model) {
        model.addAttribute("models", modelService.findModelsByBrandName(brandName));
        model.addAttribute("brand", brandName);
        model.addAttribute("offerCards", offerService.findAllCardsByBrand(brandName));
        return "offers";
    }

    @GetMapping("/{brandName}/{modelName}")
    public String getOffersByBrandAndModel(@PathVariable("brandName") String brandName,
                                           @PathVariable("modelName") String modelName,
                                           Model model) {
        model.addAttribute("offerCards", offerService.findAllCardsByBrandAndModel(brandName, modelName));
        model.addAttribute("brand", brandName);
        model.addAttribute("model", modelName);
        return "offers";
    }

    @GetMapping("/details/{id}")
    public String getOffer(@PathVariable("id")UUID uuid, Model model) {
        model.addAttribute("offer", offerService.findCardById(uuid));
        return "offer-details";
    }
}
