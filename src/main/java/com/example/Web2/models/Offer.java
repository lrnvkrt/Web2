package com.example.Web2.models;


import com.example.Web2.dtos.ModelDto;
import com.example.Web2.models.converters.EngineConverter;
import com.example.Web2.models.converters.TransmissionConverter;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "offers")
public class Offer extends AuditEntity {

    private String description;

    @Convert(converter = EngineConverter.class)
    private Engine engine;

    private String imageUrl;

    private Integer mileage;

    private BigDecimal price;

    @Convert(converter = TransmissionConverter.class)
    private Transmission transmission;

    private Integer year;

    @ManyToOne(optional = false)
    @JoinColumn(name = "model_id", referencedColumnName = "id", nullable = false)
    private Model model;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User seller;

    public enum Engine {
        GASOLINE(0),
        DIESEL(20),
        ELECTRIC(50),
        HYBRID(100);

        private int num;

        Engine(int num) {
            this.num = num;
        }

        public int getNum() {
            return num;
        }
    }

    public enum Transmission {
        MANUAL(0),
        AUTOMATIC(20);
        private int num;
        Transmission(int num) {
            this.num = num;
        }
        public int getNum() {
            return num;
        }
    }

    protected Offer() {}

    public Offer(String description, Engine engine, String imageUrl, Integer mileage, BigDecimal price, Transmission transmission, Integer year, Model model, User seller) {
        this.description = description;
        this.engine = engine;
        this.imageUrl = imageUrl;
        this.mileage = mileage;
        this.price = price;
        this.transmission = transmission;
        this.year = year;
        this.model = model;
        this.seller = seller;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "description='" + description + '\'' +
                ", engine=" + engine +
                ", imageUrl='" + imageUrl + '\'' +
                ", mileage=" + mileage +
                ", price=" + price +
                ", transmission=" + transmission +
                ", year=" + year +
                ", model=" + model +
                ", seller=" + seller +
                '}';
    }
}
