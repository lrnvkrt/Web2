package com.example.Web2.services;

import com.example.Web2.dtos.UserDto;
import com.example.Web2.dtos.UserRegistrationDto;
import com.example.Web2.models.User;
import com.example.Web2.models.UserRole;

import java.util.List;
import java.util.Optional;

public interface UserService<ID> {

    void register(UserRegistrationDto userRegistrationDto);

    UserDto addUser(UserDto userDto);

    UserDto updateUser(UserDto userDto);

    void changeUsername(String oldUsername, String newUsername);

    boolean changePassword(String username, String oldPassword, String newPassword);

    void changeActivation(String username);

    void changeRole(String username, UserRole.Role roleName);

    User findByUsername(String username);

    UserDto findUserDtoByUsername(String username);

    void deleteUser(ID id);

    List<UserDto> findAllUsers();
}
