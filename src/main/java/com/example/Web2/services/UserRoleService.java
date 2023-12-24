package com.example.Web2.services;

import com.example.Web2.dtos.UserRoleDto;
import com.example.Web2.models.UserRole;

import java.util.List;
import java.util.UUID;

public interface UserRoleService<ID> {

    UserRoleDto addUserRole(UserRoleDto userRoleDto);

    UserRoleDto findById(ID id);

    UserRole findByRoleName(UserRole.Role roleName);


    List<UserRoleDto> findAllUserRoles();

}
