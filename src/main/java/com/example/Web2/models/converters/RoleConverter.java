package com.example.Web2.models.converters;

import com.example.Web2.models.UserRole;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class RoleConverter implements AttributeConverter<UserRole.Role, Integer> {
    @Override
    public Integer convertToDatabaseColumn(UserRole.Role role) {
        if (role == null) {
            return null;
        }
        return role.getNum();
    }

    @Override
    public UserRole.Role convertToEntityAttribute(Integer integer) {
        UserRole.Role[] roles = UserRole.Role.class.getEnumConstants();
        for (UserRole.Role role : roles) {
            if (role.getNum() == integer) {
                return role;
            }
        }
        return null;
    }
}
