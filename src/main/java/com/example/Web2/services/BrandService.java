package com.example.Web2.services;

import com.example.Web2.dtos.BrandDto;
import com.example.Web2.models.Brand;

import java.util.List;

public interface BrandService<ID> {

    BrandDto addBrand(BrandDto brandDto);

    BrandDto updateBrand(BrandDto brandDto);

    Brand findBrandByName(String name);

    void deleteBrand(ID id);

    List<BrandDto> findAllBrands();
}
