package com.example.Web2.dtos;

import com.example.Web2.util.FieldsValueMatch;
import com.example.Web2.util.UniqueUsername;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@FieldsValueMatch(field = "password", fieldMatch = "confirmPassword", message = "Пароли не совпадают!")
public class UserRegistrationDto {

    @UniqueUsername
    private String username;

    private String password;

    private String confirmPassword;
    private String firstName;
    private String lastName;
    private String imageUrl;

    public UserRegistrationDto() {
    }

    public UserRegistrationDto(String username, String password, String confirmPassword, String firstName, String lastName, String imageUrl) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageUrl = imageUrl;
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
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
