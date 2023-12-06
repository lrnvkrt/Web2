package com.example.Web2.models;

import com.example.Web2.models.converters.CategoryConverter;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "models")
public class Model extends AuditEntity {
    private String name;
    @Convert(converter = CategoryConverter.class)
    private Category category;
    private String imageUrl;
    private Integer startYear;
    private Integer endYear;

    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "brand_id", referencedColumnName = "id", nullable = false)
    private Brand brand;

    @OneToMany(mappedBy = "model")
    private Set<Offer> offers;

    public enum Category {
        Car(0),
        Buss(10),
        Truck(20),
        Motorcycle(50);

        private int num;

        Category(int num) {
            this.num = num;
        }

        public int getNum() {
            return num;
        }

    }

    protected Model() {}

    public Model(String name, Category category, String imageUrl, Integer startYear, Integer endYear, Brand brand) {
        this.name = name;
        this.category = category;
        this.imageUrl = imageUrl;
        this.startYear = startYear;
        this.endYear = endYear;
        this.brand = brand;
    }

    @Column(unique = true, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getStartYear() {
        return startYear;
    }

    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }

    public Integer getEndYear() {
        return endYear;
    }

    public void setEndYear(Integer endYear) {
        this.endYear = endYear;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

}
