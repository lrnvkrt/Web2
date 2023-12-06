package com.example.Web2.services;

import com.example.Web2.dtos.UserDto;
import com.example.Web2.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService<ID> {

    UserDto addUser(UserDto userDto);

    UserDto updateUser(UserDto userDto);

    void changeActivation(String username);

    User findByUsername(String username);

    void deleteUser(ID id);

    List<UserDto> findAllUsers();
}
