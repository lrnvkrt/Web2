package com.example.Web2.config;

import com.example.Web2.dtos.ModelDto;
import com.example.Web2.dtos.OfferDto;
import com.example.Web2.dtos.UserDto;
import com.example.Web2.models.Model;
import com.example.Web2.models.Offer;
import com.example.Web2.models.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<Model, ModelDto> modelPropertyMapper = modelMapper.createTypeMap(Model.class,
                ModelDto.class);
        modelPropertyMapper.addMapping(
                model -> model.getBrand().getName(), ModelDto::setBrandName
        );
        TypeMap<User, UserDto> userPropertyMapper = modelMapper.createTypeMap(User.class,
                UserDto.class);
        userPropertyMapper.addMapping(
                User::getUserRole, UserDto::setUserRoleDto
        );
        TypeMap<Offer, OfferDto> offerPropertyMapper = modelMapper.createTypeMap(Offer.class,
                OfferDto.class);
        offerPropertyMapper.addMapping(
                offer -> offer.getModel().getId(), OfferDto::setModelUuid
        );
        offerPropertyMapper.addMapping(
                offer -> offer.getSeller().getUsername(), OfferDto::setSellerUsername
        );
        return modelMapper;
    }
}
