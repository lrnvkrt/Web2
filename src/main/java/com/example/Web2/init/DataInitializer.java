package com.example.Web2.init;

import com.example.Web2.dtos.*;
import com.example.Web2.models.Model;
import com.example.Web2.models.Offer;
import com.example.Web2.models.UserRole;
import com.example.Web2.services.impl.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class DataInitializer implements CommandLineRunner {

    private final BrandServiceImpl brandService;

    private final ModelServiceImpl modelService;

    private final UserRoleServiceImpl userRoleService;

    private final UserServiceImpl userService;

    private final OfferServiceImpl offerService;

    @Autowired
    public DataInitializer(BrandServiceImpl brandService, ModelServiceImpl modelService, UserRoleServiceImpl userRoleService, UserServiceImpl userService, OfferServiceImpl offerService) {
        this.brandService = brandService;
        this.modelService = modelService;
        this.userRoleService = userRoleService;
        this.userService = userService;
        this.offerService = offerService;
    }


    @Override
    public void run(String... args) throws Exception {
        seedData();
    }

    private void seedData() {
        BrandDto brandDto = new BrandDto("Volvo");
        BrandDto savedBrand = brandService.addBrand(brandDto);
        BrandDto brandDto1 = new BrandDto("BMW");
        BrandDto savedBrand1 = brandService.addBrand(brandDto1);
        savedBrand1.setName("Tesla");
        brandService.updateBrand(savedBrand1);

        ModelDto modelDto = new ModelDto("X16", Model.Category.Car, null, 2012, 2018, savedBrand.getName());
        ModelDto savedModel = modelService.addModel(modelDto);
        ModelDto modelDto1 = new ModelDto("VRUM-VRUM", Model.Category.Car, null, 1980, 2080, savedBrand.getName());
        ModelDto savedModel1 = modelService.addModel(modelDto1);
        savedModel.setEndYear(2020);
        savedModel.setImageUrl("фотка_классной_машины.png");
        modelService.updateModel(savedModel);
        System.out.println(savedModel);

        UserRoleDto userRoleDto = new UserRoleDto(UserRole.Role.User);
        UserRoleDto savedRoleUser = userRoleService.addUserRole(userRoleDto);
        UserRoleDto userRoleDto1 = new UserRoleDto(UserRole.Role.Admin);
        UserRoleDto savedRoleAdmin = userRoleService.addUserRole(userRoleDto1);
        System.out.println("Искомая роль!!! " + userRoleService.findById(savedRoleUser.getId()));

        UserDto userDto = new UserDto("touareg_3000", "12345GS", "Арсений", "Дубровский", "сенечка.jpg", savedRoleAdmin);
        UserDto savedUser = userService.addUser(userDto);
        UserDto userDto1 = new UserDto("not_bad_seller", "88888", "Александра", "Тесла", "саня.jpeg", savedRoleUser);
        UserDto savedUser1 = userService.addUser(userDto1);
        savedUser1.setPassword("99899");
        userService.updateUser(savedUser1);
        userService.changeActivation(savedUser1.getUsername());

        OfferDto offerDto = new OfferDto("Самая лучшая машина!", Offer.Engine.DIESEL, "лучшая_машина.jpg", 100, new BigDecimal(1_000_000), Offer.Transmission.AUTOMATIC, 2022, savedModel.getId(), savedUser.getUsername());
        OfferDto savedOffer = offerService.addOffer(offerDto);
        OfferDto offerDto1 = new OfferDto("Тоже неплохая машина!", Offer.Engine.ELECTRIC, "чуть_хуже_машина.jpg", 1000, new BigDecimal(15_000_000), Offer.Transmission.AUTOMATIC, 2023, savedModel1.getId(), savedUser1.getUsername());
        OfferDto savedOffer1 = offerService.addOffer(offerDto1);
        savedOffer.setMileage(200);
        offerService.updateOffer(savedOffer);
        System.out.println(modelService.findAllModels());
        System.out.println(modelService.findModelsByBrandName(savedBrand.getName()));
        System.out.println(offerService.findAllOffersByActiveUsersAndModel(savedModel.getId()));
    }
}
