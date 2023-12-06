package com.example.Web2.dtos;

import com.example.Web2.models.Model;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

public class ModelDto extends BaseEntityDto{
    private String name;
    @Enumerated(EnumType.STRING)
    private Model.Category category;
    private String imageUrl;
    private Integer startYear;
    private Integer endYear;
    private String brandName;

    public ModelDto() {}

    public ModelDto(String name, Model.Category category, String imageUrl, Integer startYear, Integer endYear, String brandDto) {
        this.name = name;
        this.category = category;
        this.imageUrl = imageUrl;
        this.startYear = startYear;
        this.endYear = endYear;
        this.brandName = brandDto;
    }

    @NotNull
    @NotEmpty
    @Length(min = 2, message = "Model's name length must be more than two characters!")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Model.Category getCategory() {
        return category;
    }
    public void setCategory(Model.Category category) {
        this.category = category;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    @Positive
    public Integer getStartYear() {
        return startYear;
    }
    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }
    @Positive
    public Integer getEndYear() {
        return endYear;
    }
    public void setEndYear(Integer endYear) {
        this.endYear = endYear;
    }
    @NotNull
    @NotEmpty
    @Length(min = 2, message = "Brand's name must be more than two characters!")
    public String getBrandName() {
        return brandName;
    }
    public void setBrandDto(String brandDto) {
        this.brandName = brandDto;
    }


    @Override
    public String toString() {
        return "ModelDto{" +
                "name='" + name + '\'' +
                ", category=" + category +
                ", imageUrl='" + imageUrl + '\'' +
                ", startYear=" + startYear +
                ", endYear=" + endYear +
                ", brandName='" + brandName + '\'' +
                '}';
    }
}
