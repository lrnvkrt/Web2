package com.example.Web2.models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "brands")
public class Brand extends AuditEntity {
    private String name;
    @OneToMany(mappedBy = "brand")
    private Set<Model> models;

    protected Brand() {}

    public Brand(String name) {
        this.name = name;
    }

    @Column(name = "name", unique = true, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Set<Model> getModels() {
        return models;
    }

    public void setModels(Set<Model> models) {
        this.models = models;
    }
}
