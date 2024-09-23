package com.hps.admindashboardservice.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleDTO {
    private Long id;
    private String label;
    private List<UserRoleDTO> userRoles;
    private List<RolePermissionDTO> rolePermissions;


    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<UserRoleDTO> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRoleDTO> userRoles) {
        this.userRoles = userRoles;
    }

    public List<RolePermissionDTO> getRolePermissions() {
        return rolePermissions;
    }

    public void setRolePermissions(List<RolePermissionDTO> rolePermissions) {
        this.rolePermissions = rolePermissions;
    }
}
