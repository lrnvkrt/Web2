package com.example.Web2.services.impl;

import com.example.Web2.dtos.UserDto;
import com.example.Web2.models.User;
import com.example.Web2.repositories.UserRepository;
import com.example.Web2.services.UserService;
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
public class UserServiceImpl implements UserService<UUID> {

    private UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final ValidationUtil validationUtil;

    @Autowired
    public UserServiceImpl(ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }
     @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        if (!this.validationUtil.isValid(userDto)) {

            this.validationUtil
                    .violations(userDto)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            throw new IllegalArgumentException("Illegal agrument!");
        }
        User user = modelMapper.map(userDto, User.class);
        user.setActive(true);
        user.setCreated(LocalDateTime.now());
        return modelMapper.map(this.userRepository.save(user), UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        if (!this.validationUtil.isValid(userDto)) {

            this.validationUtil
                    .violations(userDto)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            throw new IllegalArgumentException("Illegal agrument!");
        }
        Optional<User> oldUser = userRepository.findById(userDto.getId());
        if (oldUser.isEmpty()) {
            throw new EntityNotFoundException("Пользователя с таким ID нет: " + userDto.getId());
        }
        User user = modelMapper.map(userDto, User.class);
        user.setActive(oldUser.get().getActive());
        user.setCreated(oldUser.get().getCreated());
        user.setModified(LocalDateTime.now());
        return modelMapper.map(this.userRepository.save(user), UserDto.class);
    }

    @Override
    public void changeActivation(String username) {
        Optional<User> user = this.userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("Пользователя с таким именем пользователя нет!");
        }
        user.get().setActive(!user.get().getActive());
        user.get().setModified(LocalDateTime.now());
        this.userRepository.save(user.get());
    }

    @Override
    public User findByUsername(String username) {
        return this.userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("Пользователя с таким username нет: " + username));
    }

    @Override
    public void deleteUser(UUID uuid) {
        this.userRepository.deleteById(uuid);
    }

    @Override
    public List<UserDto> findAllUsers() {
        return this.userRepository
                .findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

}
