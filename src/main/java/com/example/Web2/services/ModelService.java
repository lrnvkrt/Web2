package com.example.Web2.services;

import com.example.Web2.dtos.ModelDto;
import com.example.Web2.models.Model;

import java.util.List;

public interface ModelService<ID> {

    ModelDto addModel(ModelDto modelDto);

    ModelDto updateModel(ModelDto modelDto);

    void deleteModel(ID id);

    List<ModelDto> findAllModels();

    List<ModelDto> findModelsByBrandName(String brandName);

    Model findModelById(ID id);

    ModelDto findById(ID id);

}
