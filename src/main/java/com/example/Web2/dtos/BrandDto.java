package com.example.Web2.dtos;

import com.example.Web2.util.UniqueBrandName;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

public class BrandDto extends BaseEntityDto {

    @UniqueBrandName
    private String name;
    public BrandDto() {}
    public BrandDto(String name) {
        this.name = name;
    }

    @NotNull
    @NotEmpty
    @Length(min = 2, message = "Brand's name length must be more than two characters!")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "BrandDto{" +
                "name='" + name + '\'' +
                '}';
    }
}
