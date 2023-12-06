package com.example.Web2.dtos;

public class OfferCardViewModel {
    private OfferDto offerDto;
    private String brandName;
    private String modelName;

    public OfferCardViewModel() {}
    public OfferCardViewModel(OfferDto offerDto, String brandName, String modelName) {
        this.offerDto = offerDto;
        this.brandName = brandName;
        this.modelName = modelName;
    }

    public OfferDto getOfferDto() {
        return offerDto;
    }

    public void setOfferDto(OfferDto offerDto) {
        this.offerDto = offerDto;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
}
