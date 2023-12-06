package com.example.Web2.services;

import com.example.Web2.dtos.OfferCardViewModel;
import com.example.Web2.dtos.OfferDto;
import com.example.Web2.models.Offer;

import java.util.List;
import java.util.UUID;

public interface OfferService<ID> {

    OfferDto addOffer(OfferDto offerDto);

    OfferDto updateOffer(OfferDto offerDto);

    void deleteOffer(ID id);

    OfferDto findById(ID id);

    List<OfferDto> findAllOffers();

    List<OfferDto> findAllOffersByActiveUsersAndModel(ID id);

    List<OfferCardViewModel> findAllCards();

    List<OfferCardViewModel> findAllCardsByBrand(String brandName);

    List<OfferCardViewModel> findAllCardsByBrandAndModel(String brandName, String modelName);

    OfferCardViewModel findCardById(ID id);
}
