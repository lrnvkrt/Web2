package com.example.Web2.models;

import com.example.Web2.models.converters.RoleConverter;
import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class UserRole extends BaseEntity {
    @Convert(converter = RoleConverter.class)
    private Role role;

    public enum Role {
        User(0),
        Admin(20);

        private int num;

        Role(int num) {
            this.num = num;
        }

        public int getNum() {
            return num;
        }

    }

    protected UserRole() {}

    public UserRole(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
