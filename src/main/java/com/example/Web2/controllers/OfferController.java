package com.example.Web2.controllers;

import com.example.Web2.dtos.OfferDto;
import com.example.Web2.services.ModelService;
import com.example.Web2.services.OfferService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.UUID;

@Controller
@RequestMapping("/offers")
public class OfferController {

    private static final Logger LOG = LogManager.getLogger(Controller.class);

    private OfferService<UUID> offerService;

    private ModelService<UUID> modelService;
    @Autowired
    public void setOfferService(OfferService offerService) {
        this.offerService = offerService;
    }
    @Autowired
    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }

    @GetMapping("/all")
    public String getAllOffers(Model model, Principal principal) {
        if(principal == null) {
            LOG.log(Level.INFO, "Show all offers.");
        } else {
            LOG.log(Level.INFO, "Show all offers for user " + principal.getName());
        }
        model.addAttribute("offerCards", offerService.findAllOfferCards());
        return "offers";
    }

    @GetMapping("/{name}")
    public String getOffersByBrand(@PathVariable("name") String brandName, Model model, Principal principal) {
        if (principal == null) {
            LOG.log(Level.INFO, "Show all offers for brand " + brandName);
        } else {
            LOG.log(Level.INFO, "Show all offers for brand " + brandName + " for user " + principal.getName());
        }
        model.addAttribute("models", modelService.findModelsByBrandName(brandName));
        model.addAttribute("brand", brandName);
        model.addAttribute("offerCards", offerService.findAllCardsByBrand(brandName));
        return "offers";
    }

    @GetMapping("/{brandName}/{modelName}")
    public String getOffersByBrandAndModel(@PathVariable("brandName") String brandName,
                                           @PathVariable("modelName") String modelName,
                                           Model model,
                                           Principal principal) {
        if (principal == null) {
            LOG.log(Level.INFO, "Show all offers for " + brandName + " " + modelName);
        } else {
            LOG.log(Level.INFO, "Show all offers for " + brandName + " " + modelName + " for user " + principal.getName());
        }
        model.addAttribute("offerCards", offerService.findAllCardsByModel(modelName));
        model.addAttribute("brand", brandName);
        model.addAttribute("model", modelName);
        return "offers";
    }

    @GetMapping("/details/{id}")
    public String getOffer(@PathVariable("id")UUID uuid, Model model, Principal principal) {
        if (principal == null) {
            LOG.log(Level.INFO, "Show details for offer with ID " + uuid);
        } else {
            LOG.log(Level.INFO, "Show details for offer with ID " + uuid + " for user " + principal.getName());
        }
        offerService.incrementWatchCount(uuid);
        model.addAttribute("offer", offerService.findCardById(uuid));
        return "offer-details";
    }

    @GetMapping("/add")
    public String getAddOffer(Model model, Principal principal) {
        OfferDto offerDto = new OfferDto();
        offerDto.setSellerUsername(principal.getName());
        model.addAttribute("newOffer", offerDto);
        model.addAttribute("availableModels", modelService.findAllModels());
        model.addAttribute("editMode", false);
        return "offer-add";
    }

    @PostMapping("/add")
    public String doAddOffer(@Valid @ModelAttribute("newOffer") OfferDto offerDto,
                             BindingResult bindingResult,
                             Principal principal,
                             Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            model.addAttribute("availableModels", modelService.findAllModels());
            model.addAttribute("editMode", false);
            return "offer-add";
        }
        offerDto.setSellerUsername(principal.getName());
        this.offerService.addOffer(offerDto);
        return "redirect:/user/my_offers";
    }

    @GetMapping("/edit/{id}")
    public String getEditOffer(@PathVariable("id") UUID uuid, Model model, Principal principal) {
        OfferDto offerDto = offerService.findById(uuid);
        model.addAttribute("newOffer", offerDto);
        model.addAttribute("availableModels", modelService.findAllModels());
        model.addAttribute("editMode", true);
        return "offer-add";
    }

    @PostMapping("/edit/{id}")
    public String doEditOffer(@PathVariable("id") UUID uuid, @Valid @ModelAttribute("newOffer") OfferDto offerDto,
                              BindingResult bindingResult,
                              Principal principal,
                              Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            model.addAttribute("availableModels", modelService.findAllModels());
            model.addAttribute("editMode", true);
            return "offer-add";
        }
        if (!offerDto.getSellerUsername().equals(principal.getName())) {
            throw new AccessDeniedException("У Вас нет доступа к данному методу!");
        }
        offerDto.setSellerUsername(principal.getName());
        this.offerService.updateOffer(offerDto);
        return "redirect:/user/my_offers";
    }


    @GetMapping("/delete/{id}")
    public String deleteOffer(@PathVariable("id") UUID uuid, Principal principal) {
        OfferDto offerDto = offerService.findById(uuid);
        if (!offerDto.getSellerUsername().equals(principal.getName())) {
            throw new AccessDeniedException("У Вас нет доступа к данному методу!");
        }
        offerService.deleteOffer(uuid);
        return "redirect:/user/my_offers";
    }
}
