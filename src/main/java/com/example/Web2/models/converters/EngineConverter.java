package com.example.Web2.models.converters;

import com.example.Web2.models.Offer;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class EngineConverter implements AttributeConverter<Offer.Engine, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Offer.Engine engine) {
        if (engine == null) {
            return null;
        }
        return engine.getNum();
    }

    @Override
    public Offer.Engine convertToEntityAttribute(Integer integer) {
        Offer.Engine[] engines = Offer.Engine.class.getEnumConstants();
        for (Offer.Engine engine : engines) {
            if (engine.getNum() == integer) {
                return engine;
            }
        }
        return null;
    }
}
