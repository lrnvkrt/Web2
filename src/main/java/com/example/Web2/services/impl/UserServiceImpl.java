package com.example.Web2.services.impl;

import com.example.Web2.dtos.UserDto;
import com.example.Web2.dtos.UserRegistrationDto;
import com.example.Web2.models.User;
import com.example.Web2.models.UserRole;
import com.example.Web2.repositories.UserRepository;
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
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@EnableCaching
public class UserServiceImpl implements UserService<UUID> {

    private UserRepository userRepository;

    private UserRoleService<UUID> userRoleService;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    private final ValidationUtil validationUtil;

    private StringRedisTemplate redisTemplate;

    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }
     @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setUserRoleService(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @Autowired
    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    @CacheEvict(cacheNames = "users", allEntries = true)
    @Override
    public void register(UserRegistrationDto registrationDto) {
        if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
            throw new RuntimeException("password.match");
        }

//        Optional<User> byUsername = this.userRepository.findByUsername(registrationDto.getUsername());
//
//        if (byUsername.isPresent()) {
//            throw new RuntimeException("username.used");
//        }

        User user = new User(
                registrationDto.getUsername(),
                passwordEncoder.encode(registrationDto.getPassword()),
                registrationDto.getFirstName(),
                registrationDto.getLastName(),
                true,
                registrationDto.getImageUrl(),
                LocalDateTime.now(),
                null,
                userRoleService.findByRoleName(UserRole.Role.User)
        );
        this.userRepository.save(user);
    }

    @CacheEvict(cacheNames = "users", allEntries = true)
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

    @CacheEvict(cacheNames = "users", allEntries = true)
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
        user.setPassword(oldUser.get().getPassword());
        user.setActive(oldUser.get().getActive());
        user.setCreated(oldUser.get().getCreated());
        user.setModified(LocalDateTime.now());
        user.setUserRole(oldUser.get().getUserRole());
        return modelMapper.map(this.userRepository.save(user), UserDto.class);
    }

    @CacheEvict(cacheNames = "users", allEntries = true)
    @Override
    public void changeUsername(String oldUsername, String newUsername) {
        User user = findByUsername(oldUsername);
        user.setUsername(newUsername);
        userRepository.save(user);
    }

    @CacheEvict(cacheNames = "users", allEntries = true)
    @Override
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        User user = findByUsername(username);
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return false;
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return true;
    }

    @CacheEvict(cacheNames = "users", allEntries = true)
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

    @CacheEvict(cacheNames = "users", allEntries = true)
    @Override
    public void changeRole(String username, UserRole.Role roleName) {
        User user = findByUsername(username);
        UserRole userRole = userRoleService.findByRoleName(roleName);
        user.setUserRole(userRole);
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return this.userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("Пользователя с таким username нет: " + username));
    }

    @Override
    public UserDto findUserDtoByUsername(String username) {
        return modelMapper.map(findByUsername(username), UserDto.class);
    }


    @Override
    public void deleteUser(UUID uuid) {
        this.userRepository.deleteById(uuid);
    }

    @Cacheable("users")
    @Override
    public List<UserDto> findAllUsers() {
        return this.userRepository
                .findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

}
