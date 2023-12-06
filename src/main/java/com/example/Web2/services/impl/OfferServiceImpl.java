package com.example.Web2.services.impl;

import com.example.Web2.dtos.ModelDto;
import com.example.Web2.dtos.OfferCardViewModel;
import com.example.Web2.dtos.OfferDto;
import com.example.Web2.models.Offer;
import com.example.Web2.repositories.OfferRepository;
import com.example.Web2.services.ModelService;
import com.example.Web2.services.OfferService;
import com.example.Web2.services.UserService;
import com.example.Web2.util.ValidationUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService<UUID> {

    private OfferRepository offerRepository;
    private UserService<UUID> userService;
    private ModelService<UUID> modelService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public OfferServiceImpl(ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Autowired
    public void setOfferRepository(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }
    @Autowired
    public void setUserService(UserService<UUID> userService) {
        this.userService = userService;
    }
    @Autowired
    public void setModelService(ModelService<UUID> modelService) {
        this.modelService = modelService;
    }

    @Override
    public OfferDto addOffer(OfferDto offerDto) {
        if (!this.validationUtil.isValid(offerDto)) {

            this.validationUtil
                    .violations(offerDto)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            throw new IllegalArgumentException("Illegal agrument!");
        }
        Offer offer = modelMapper.map(offerDto, Offer.class);
        offer.setCreated(LocalDateTime.now());
        offer.setSeller(userService.findByUsername(offerDto.getSellerUsername()));
        offer.setModel(modelService.findModelById(offerDto.getModelUuid()));
        Offer savedOffer = this.offerRepository.save(offer);
        System.out.println(savedOffer);
        return modelMapper.map(savedOffer, OfferDto.class);
    }

    @Override
    public OfferDto updateOffer(OfferDto offerDto) {
        if (!this.validationUtil.isValid(offerDto)) {

            this.validationUtil
                    .violations(offerDto)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            throw new IllegalArgumentException("Illegal agrument!");
        }
        Optional<Offer> oldOffer = this.offerRepository.findById(offerDto.getId());
        if (oldOffer.isEmpty()) {
            throw new EntityNotFoundException("Предложения с таким ID нет: " + offerDto.getId());
        }
        Offer offer = modelMapper.map(offerDto, Offer.class);
        offer.setCreated(oldOffer.get().getCreated());
        offer.setModified(LocalDateTime.now());
        offer.setModel(modelService.findModelById(offerDto.getModelUuid()));
        offer.setSeller(userService.findByUsername(offerDto.getSellerUsername()));
        Offer savedOffer = this.offerRepository.save(offer);
        return modelMapper.map(savedOffer, OfferDto.class);
    }

    @Override
    public void deleteOffer(UUID uuid) {
        this.offerRepository.deleteById(uuid);
    }

    @Override
    public OfferDto findById(UUID uuid) {
        return modelMapper.map(this.offerRepository.findById(uuid).orElseThrow(() -> new EntityNotFoundException("Предложения с таким ID нет!")), OfferDto.class);
    }

    @Override
    public List<OfferDto> findAllOffers() {
        return this.offerRepository
                .findAll()
                .stream()
                .map(offer -> modelMapper.map(offer, OfferDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OfferDto> findAllOffersByActiveUsersAndModel(UUID uuid) {
        return offerRepository
                .findAllOffersByActiveUsersAndModel(uuid)
                .stream()
                .map(offer -> modelMapper.map(offer, OfferDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OfferCardViewModel> findAllCards() {
        List<OfferCardViewModel> cards = new ArrayList<>();
        List<OfferDto> offers = findAllOffers();
        for (OfferDto offer : offers) {
            ModelDto modelDto = modelService.findById(offer.getModelUuid());
            cards.add(new OfferCardViewModel(offer, modelDto.getBrandName(), modelDto.getName()));
        }
        return cards;
    }

    @Override
    public List<OfferCardViewModel> findAllCardsByBrand(String brandName) {
        return findAllCards()
                .stream()
                .filter(offerCardViewModel -> offerCardViewModel.getBrandName().equals(brandName))
                .collect(Collectors.toList());
    }

    @Override
    public List<OfferCardViewModel> findAllCardsByBrandAndModel(String brandName, String modelName) {
        return findAllCardsByBrand(brandName)
                .stream()
                .filter(offerCardViewModel -> offerCardViewModel.getModelName().equals(modelName))
                .collect(Collectors.toList());
    }

    @Override
    public OfferCardViewModel findCardById(UUID uuid) {
        OfferDto offer = findById(uuid);
        ModelDto modelDto = modelService.findById(offer.getModelUuid());
        return new OfferCardViewModel(offer, modelDto.getBrandName(), modelDto.getName());
    }
}
