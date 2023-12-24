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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@EnableCaching
public class OfferServiceImpl implements OfferService<UUID> {

    private OfferRepository offerRepository;
    private UserService<UUID> userService;
    private ModelService<UUID> modelService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private RedisTemplate<String, String> redisTemplate;
    private static final String WATCH_COUNT_KEY = "offer:watch:count";

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

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    @CacheEvict(cacheNames = "offers", allEntries = true)
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
        return modelMapper.map(savedOffer, OfferDto.class);
    }

    @CacheEvict(cacheNames = "offers", allEntries = true)
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

    @CacheEvict(cacheNames = "offers", allEntries = true)
    @Override
    public void deleteOffer(UUID uuid) {
        this.offerRepository.deleteById(uuid);
    }


    @Override
    public OfferDto findById(UUID uuid) {
        return modelMapper.map(this.offerRepository.findById(uuid).orElseThrow(() -> new EntityNotFoundException("Предложения с таким ID нет!")), OfferDto.class);
    }

    @Cacheable("offers")
    @Override
    public List<OfferDto> findAllOffers() {
        return this.offerRepository
                .findAll()
                .stream()
                .map(offer -> modelMapper.map(offer, OfferDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OfferDto> findAllOffersByBrand(String brandName) {
        return this.offerRepository
                .findByBrandName(brandName)
                .stream()
                .map(offer -> modelMapper.map(offer, OfferDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OfferDto> findAllOffersByModel(String modelName) {
        return this.offerRepository
                .findAllByModelName(modelName)
                .stream()
                .map(offer -> modelMapper.map(offer, OfferDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OfferDto> findAllOffersByUsername(String username) {
        return this.offerRepository
                .findAllBySellerUsername(username)
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
    public List<OfferCardViewModel> findAllOfferCards() {
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
        List<OfferCardViewModel> cards = new ArrayList<>();
        List<OfferDto> offers = findAllOffersByBrand(brandName);
        for (OfferDto offer : offers) {
            ModelDto modelDto = modelService.findById(offer.getModelUuid());
            cards.add(new OfferCardViewModel(offer, modelDto.getBrandName(), modelDto.getName()));
        }
        return cards;
    }

    @Override
    public List<OfferCardViewModel> findAllCardsByModel(String modelName) {
        List<OfferCardViewModel> cards = new ArrayList<>();
        List<OfferDto> offers = findAllOffersByModel(modelName);
        for (OfferDto offer : offers) {
            ModelDto modelDto = modelService.findById(offer.getModelUuid());
            cards.add(new OfferCardViewModel(offer, modelDto.getBrandName(), modelDto.getName()));
        }
        return cards;
    }

    @Override
    public OfferCardViewModel findCardById(UUID uuid) {
        OfferDto offer = findById(uuid);
        ModelDto modelDto = modelService.findById(offer.getModelUuid());
        return new OfferCardViewModel(offer, modelDto.getBrandName(), modelDto.getName());
    }

    @Override
    public List<OfferCardViewModel> findCardsByUsername(String username) {
        List<OfferCardViewModel> cards = new ArrayList<>();
        List<OfferDto> offers = findAllOffersByUsername(username);
        for (OfferDto offer : offers) {
            ModelDto modelDto = modelService.findById(offer.getModelUuid());
            cards.add(new OfferCardViewModel(offer, modelDto.getBrandName(), modelDto.getName()));
        }
        return cards;
    }

    @Override
    public List<OfferCardViewModel> findNewOffers() {
        List<OfferCardViewModel> cards = new ArrayList<>();
        List<OfferDto> offers = this.offerRepository
                .findTop5ByOrderByCreatedDesc()
                .stream()
                .map(offer -> modelMapper.map(offer, OfferDto.class))
                .toList();
        for (OfferDto offer : offers) {
            ModelDto modelDto = modelService.findById(offer.getModelUuid());
            cards.add(new OfferCardViewModel(offer, modelDto.getBrandName(), modelDto.getName()));
        }
        return cards;
    }

    public void incrementWatchCount(UUID offerId) {
        redisTemplate.opsForZSet().incrementScore(WATCH_COUNT_KEY, offerId.toString(), 1);
    }

    public Set<OfferCardViewModel> getTopOffers(int count) {
        // Retrieve the top N offers based on watch counts
        Set<String> list = redisTemplate.opsForZSet().range(WATCH_COUNT_KEY, 0, count - 1);
        if (list != null) {
            return list.stream().map(UUID::fromString).map(this::findCardById).collect(Collectors.toSet());
        }
        return new HashSet<>();
    }

}
