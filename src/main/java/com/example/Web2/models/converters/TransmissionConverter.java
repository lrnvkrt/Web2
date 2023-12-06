package com.example.Web2.models.converters;

import com.example.Web2.models.Offer;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class TransmissionConverter implements AttributeConverter<Offer.Transmission, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Offer.Transmission transmission) {
        if (transmission == null) {
            return null;
        }
        return transmission.getNum();
    }

    @Override
    public Offer.Transmission convertToEntityAttribute(Integer integer) {
        Offer.Transmission[] transmissions = Offer.Transmission.class.getEnumConstants();
        for (Offer.Transmission transmission : transmissions) {
            if (transmission.getNum() == integer) {
                return transmission;
            }
        }
        return null;
    }
}
