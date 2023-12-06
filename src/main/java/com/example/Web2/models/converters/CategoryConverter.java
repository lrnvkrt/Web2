package com.example.Web2.models.converters;

import com.example.Web2.models.Model;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CategoryConverter implements AttributeConverter<Model.Category, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Model.Category category) {
        if (category == null) {
            return null;
        }
        return category.getNum();
    }

    @Override
    public Model.Category convertToEntityAttribute(Integer integer) {
        Model.Category[] categories = Model.Category.class.getEnumConstants();
        for (Model.Category category: categories) {
            if (category.getNum() == integer) {
                return category;
            }
        }
        return null;
    }
}
