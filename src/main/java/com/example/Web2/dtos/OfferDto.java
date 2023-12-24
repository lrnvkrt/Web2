package com.example.Web2.dtos;

import com.example.Web2.models.Offer;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.UUID;

public class OfferDto extends BaseEntityDto{
    private String description;

    private Offer.Engine engine;

    private String imageUrl;

    private Integer mileage;

    private BigDecimal price;

    private Offer.Transmission transmission;

    private Integer year;

    private UUID modelUuid;

    private String sellerUsername;

    public OfferDto() {}

    public OfferDto(String description, Offer.Engine engine, String imageUrl, Integer mileage, BigDecimal price, Offer.Transmission transmission, Integer year, UUID modelDto, String sellerDto) {
        this.description = description;
        this.engine = engine;
        this.imageUrl = imageUrl;
        this.mileage = mileage;
        this.price = price;
        this.transmission = transmission;
        this.year = year;
        this.modelUuid = modelDto;
        this.sellerUsername = sellerDto;
    }
    @Length(max = 150, message = "Description must be less than 150 characters!")
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Offer.Engine getEngine() {
        return engine;
    }
    public void setEngine(Offer.Engine engine) {
        this.engine = engine;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    @Positive
    public Integer getMileage() {
        return mileage;
    }
    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }
    @DecimalMin(value = "0")
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public Offer.Transmission getTransmission() {
        return transmission;
    }
    public void setTransmission(Offer.Transmission transmission) {
        this.transmission = transmission;
    }
    @Positive
    public Integer getYear() {
        return year;
    }
    public void setYear(Integer year) {
        this.year = year;
    }

    @NotNull
    public UUID getModelUuid() {
        return modelUuid;
    }
    public void setModelUuid(UUID modelUuid) {
        this.modelUuid = modelUuid;
    }

    @NotNull
    @NotEmpty
    @Length(min = 2, message = "Username must be more than two characters!")
    public String getSellerUsername() {
        return sellerUsername;
    }
    public void setSellerUsername(String sellerUsername) {
        this.sellerUsername = sellerUsername;
    }

    @Override
    public String toString() {
        return "OfferDto{" +
                "description='" + description + '\'' +
                ", engine=" + engine +
                ", imageUrl='" + imageUrl + '\'' +
                ", mileage=" + mileage +
                ", price=" + price +
                ", transmission=" + transmission +
                ", year=" + year +
                ", modelUuid=" + modelUuid +
                ", sellerUsername=" + sellerUsername +
                '}';
    }
}
