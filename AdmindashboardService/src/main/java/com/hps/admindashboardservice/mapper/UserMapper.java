package com.hps.admindashboardservice.mapper;
import com.hps.admindashboardservice.dto.PermissionDTO;
import com.hps.admindashboardservice.dto.UserRoleDTO;
import com.hps.admindashboardservice.entities.Permission;
import com.hps.admindashboardservice.entities.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import com.hps.admindashboardservice.dto.UserDTO;
import com.hps.admindashboardservice.entities.user;

@Mapper(componentModel = "spring")
public interface UserMapper {
    // Mapping from UserDTO to User entity and vice versa
    user toEntity(UserDTO dto);
    UserDTO toDto(user entity);

    // Role mapping with permissions (Adjusted based on DTO changes)
    @Mapping(target = "rolePermissions", source = "rolePermissions")
    UserRoleDTO toDto(Role role);

    @Mapping(target = "rolePermissions", source = "rolePermissions")
    Role toEntity(UserRoleDTO rolesDTO);

    // Permission mapping
    PermissionDTO toDto(Permission permission);
    Permission toEntity(PermissionDTO permissionDTO);

    // Updating entity from DTO
    void updateEntityFromDto(UserDTO dto, @MappingTarget user entity);
}

