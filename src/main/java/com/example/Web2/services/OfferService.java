package com.example.Web2.services;

import com.example.Web2.dtos.OfferCardViewModel;
import com.example.Web2.dtos.OfferDto;
import com.example.Web2.models.Offer;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface OfferService<ID> {

    OfferDto addOffer(OfferDto offerDto);

    OfferDto updateOffer(OfferDto offerDto);

    void deleteOffer(ID id);

    OfferDto findById(ID id);

    List<OfferDto> findAllOffers();

    List<OfferDto> findAllOffersByBrand(String brandName);

    List<OfferDto> findAllOffersByModel(String modelName);

    List<OfferDto> findAllOffersByUsername(String username);

    List<OfferDto> findAllOffersByActiveUsersAndModel(ID id);

    List<OfferCardViewModel> findAllOfferCards();

    List<OfferCardViewModel> findAllCardsByBrand(String brandName);

    List<OfferCardViewModel> findAllCardsByModel(String modelName);

    OfferCardViewModel findCardById(ID id);

    List<OfferCardViewModel> findCardsByUsername(String username);

    List<OfferCardViewModel> findNewOffers();

    void incrementWatchCount(UUID uuid);

    Set<OfferCardViewModel> getTopOffers(int count);

}
