package com.example.Web2.dtos;

import jakarta.persistence.MappedSuperclass;

import java.util.UUID;

public abstract class BaseEntityDto {
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
