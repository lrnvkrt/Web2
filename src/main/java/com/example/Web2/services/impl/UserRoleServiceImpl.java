package com.example.Web2.services.impl;

import com.example.Web2.dtos.UserDto;
import com.example.Web2.dtos.UserRoleDto;
import com.example.Web2.models.User;
import com.example.Web2.models.UserRole;
import com.example.Web2.repositories.UserRepository;
import com.example.Web2.repositories.UserRoleRepository;
import com.example.Web2.services.UserRoleService;
import com.example.Web2.services.UserService;
import com.example.Web2.util.ValidationUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@EnableCaching
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

    @CacheEvict(cacheNames = "roles", allEntries = true)
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

    @CacheEvict(cacheNames = "roles", allEntries = true)
    @Override
    public UserRoleDto findById(UUID uuid) {
        return modelMapper.map(this.userRoleRepository.findById(uuid).orElseThrow(() -> new EntityNotFoundException("Роли с таким ID нет!")), UserRoleDto.class);
    }

    @Override
    public UserRole findByRoleName(UserRole.Role roleName) {
        return this.userRoleRepository.findUserRoleByRole(roleName)
                .orElseThrow(() -> new EntityNotFoundException("Роли с таким именем нет!"));
    }



    @Cacheable("roles")
    @Override
    public List<UserRoleDto> findAllUserRoles() {
        return this.userRoleRepository
                .findAll()
                .stream()
                .map(userRole -> modelMapper.map(userRole, UserRoleDto.class))
                .collect(Collectors.toList());
    }


}
