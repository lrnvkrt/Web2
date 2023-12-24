package com.example.Web2.dtos;

import com.example.Web2.util.UniqueUsername;

public class UsernameDto {

    @UniqueUsername(message = "Ваш никнейм либо занят, либо уже используется Вами!")
    private String username;

    public UsernameDto() {
    }

    public UsernameDto(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
