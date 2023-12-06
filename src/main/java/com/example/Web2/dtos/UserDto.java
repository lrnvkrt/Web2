package com.example.Web2.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserDto extends BaseEntityDto{
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Boolean isActive;
    private String imageUrl;
    private UserRoleDto userRoleDto;

    public UserDto() {}

    public UserDto(String username, String password, String firstName, String lastName, String imageUrl, UserRoleDto userRoleDto) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageUrl = imageUrl;
        this.userRoleDto = userRoleDto;
    }

    @NotNull
    @NotEmpty
    @Length(min = 2, message = "Username must be more than two characters!")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotNull
    @NotEmpty
    @Length(min = 5, message = "Password must be greater than five characters!")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotNull
    @NotEmpty
    @Length(min = 2, message = "First name must be greater than five characters!")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NotNull
    @NotEmpty
    @Length(min = 2, message = "Last name must be greater than five characters!")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @NotNull
    public UserRoleDto getUserRoleDto() {
        return userRoleDto;
    }

    public void setUserRoleDto(UserRoleDto userRoleDto) {
        this.userRoleDto = userRoleDto;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", isActive=" + isActive +
                ", imageUrl='" + imageUrl + '\'' +
                ", userRoleDto=" + userRoleDto +
                '}';
    }
}
