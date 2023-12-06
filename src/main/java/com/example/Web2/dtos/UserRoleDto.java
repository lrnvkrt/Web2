package com.example.Web2.dtos;

import com.example.Web2.models.UserRole;

import java.util.UUID;

public class UserRoleDto extends BaseEntityDto{
    private UserRole.Role role;
    public UserRoleDto() {}
    public UserRoleDto(UserRole.Role role) {
        this.role = role;
    }

    public UserRole.Role getRole() {
        return role;
    }

    public void setRole(UserRole.Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserRoleDto{" +
                "role=" + role +
                '}';
    }
}
