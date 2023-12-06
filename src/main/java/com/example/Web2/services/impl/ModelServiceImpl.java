package com.example.Web2.services.impl;

import com.example.Web2.dtos.ModelDto;
import com.example.Web2.models.Model;
import com.example.Web2.repositories.ModelRepository;
import com.example.Web2.services.BrandService;
import com.example.Web2.services.ModelService;
import com.example.Web2.util.ValidationUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ModelServiceImpl implements ModelService<UUID> {

    private ModelRepository modelRepository;
    private BrandService<UUID> brandService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public ModelServiceImpl(ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Autowired
    public void setModelRepository(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    @Autowired
    public void setBrandService(BrandService<UUID> brandService) {
        this.brandService = brandService;
    }

    @Override
    public ModelDto addModel(ModelDto modelDto) {
        if (!this.validationUtil.isValid(modelDto)) {

            this.validationUtil
                    .violations(modelDto)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            throw new IllegalArgumentException("Illegal agrument!");
        }
        Model model = modelMapper.map(modelDto, Model.class);
        model.setCreated(LocalDateTime.now());
        model.setBrand(this.brandService.findBrandByName(modelDto.getBrandName()));
        return modelMapper.map(this.modelRepository.save(model), ModelDto.class);
    }

    @Override
    public ModelDto updateModel(ModelDto modelDto) {
        if (!this.validationUtil.isValid(modelDto)) {

            this.validationUtil
                    .violations(modelDto)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            throw new IllegalArgumentException("Illegal agrument!");
        }
        Optional<Model> oldModel = this.modelRepository.findById(modelDto.getId());
        if (oldModel.isEmpty()) {
            throw new EntityNotFoundException("Модели с таким ID нет: " + modelDto.getId());
        }
        Model model = modelMapper.map(modelDto, Model.class);
        model.setCreated(oldModel.get().getCreated());
        model.setModified(LocalDateTime.now());
        model.setBrand(oldModel.get().getBrand());
        return modelMapper.map(this.modelRepository.save(model), ModelDto.class);
    }

    @Override
    public void deleteModel(UUID uuid) {
        this.modelRepository.deleteById(uuid);
    }

    @Override
    public List<ModelDto> findAllModels() {
        return this.modelRepository
                .findAll()
                .stream()
                .map(model -> modelMapper.map(model, ModelDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ModelDto> findModelsByBrandName(String brandName) {
        return modelRepository
                .findModelsByBrandName(brandName)
                .stream()
                .map(model -> modelMapper.map(model, ModelDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Model findModelById(UUID uuid) {
        return this.modelRepository.findById(uuid).orElseThrow(() -> new EntityNotFoundException("Модели с таким ID нет: " + uuid));
    }

    @Override
    public ModelDto findById(UUID uuid) {
        return modelMapper.map(findModelById(uuid), ModelDto.class);
    }


}
