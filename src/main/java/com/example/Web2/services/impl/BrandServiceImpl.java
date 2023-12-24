package com.example.Web2.services.impl;

import com.example.Web2.dtos.BrandDto;
import com.example.Web2.models.Brand;
import com.example.Web2.repositories.BrandRepository;
import com.example.Web2.services.BrandService;
import com.example.Web2.util.ValidationUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@EnableCaching
public class BrandServiceImpl implements BrandService<UUID> {

    private BrandRepository brandRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public BrandServiceImpl(ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Autowired
    public void setBrandRepository(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @CacheEvict(cacheNames = "brands", allEntries = true)
    @Override
    public BrandDto addBrand(BrandDto brandDto) {
        if (!this.validationUtil.isValid(brandDto)) {

            this.validationUtil
                    .violations(brandDto)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            throw new IllegalArgumentException("Illegal agrument!");
        }
        Brand brand = modelMapper.map(brandDto, Brand.class);
        brand.setCreated(LocalDateTime.now());
        return modelMapper.map(this.brandRepository.save(brand), BrandDto.class);

    }

    @CacheEvict(cacheNames = "brands", allEntries = true)
    @Override
    public BrandDto updateBrand(BrandDto brandDto) {
        if (!this.validationUtil.isValid(brandDto)) {
            this.validationUtil
                    .violations(brandDto)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            throw new IllegalArgumentException("Illegal agrument!");
        }
        Optional<Brand> oldBrand = this.brandRepository.findById(brandDto.getId());
        if (oldBrand.isEmpty()) {
            throw new EntityNotFoundException("Бренда с таким ID нет: " + brandDto.getId());
        }
        Brand brand = modelMapper.map(brandDto, Brand.class);
        brand.setCreated(oldBrand.get().getCreated());
        brand.setModified(LocalDateTime.now());
        return modelMapper.map(this.brandRepository.save(brand), BrandDto.class);
    }

    @Override
    public Brand findBrandByName(String name) {
        return this.brandRepository.findBrandByName(name).orElseThrow();
    }

    @CacheEvict(cacheNames = "brands", allEntries = true)
    @Override
    public void deleteBrand(UUID uuid) {
        this.brandRepository.deleteById(uuid);
    }

    @Cacheable("brands")
    @Override
    public List<BrandDto> findAllBrands() {
        return this.brandRepository
                .findAll()
                .stream()
                .map(brand -> modelMapper.map(brand, BrandDto.class))
                .collect(Collectors.toList());
    }
}
