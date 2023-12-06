package com.example.Web2.services.impl;

import com.example.Web2.dtos.UserRoleDto;
import com.example.Web2.models.UserRole;
import com.example.Web2.repositories.UserRoleRepository;
import com.example.Web2.services.UserRoleService;
import com.example.Web2.util.ValidationUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserRoleServiceImpl implements UserRoleService<UUID> {

    private UserRoleRepository userRoleRepository;

    private final ModelMapper modelMapper;

    private final ValidationUtil validationUtil;
    @Autowired
    public UserRoleServiceImpl(ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Autowired
    public void setUserRoleRepository(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;

    }

    @Override
    public UserRoleDto addUserRole(UserRoleDto userRoleDto) {
        if (!this.validationUtil.isValid(userRoleDto)) {

            this.validationUtil
                    .violations(userRoleDto)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            throw new IllegalArgumentException("Illegal agrument!");
        }
        UserRole userRole = modelMapper.map(userRoleDto, UserRole.class);
        return modelMapper.map(this.userRoleRepository.save(userRole), UserRoleDto.class);
    }

    @Override
    public UserRoleDto findById(UUID uuid) {
        return modelMapper.map(this.userRoleRepository.findById(uuid).orElseThrow(() -> new EntityNotFoundException("Роли с таким ID нет!")), UserRoleDto.class);
    }


    @Override
    public List<UserRoleDto> findAllUserRoles() {
        return this.userRoleRepository
                .findAll()
                .stream()
                .map(userRole -> modelMapper.map(userRole, UserRoleDto.class))
                .collect(Collectors.toList());
    }
}
