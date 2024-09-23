package com.hps.admindashboardservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private long id;
    private String username;
    private String password;
    private String imageUrl;
    private String email;
    private String role;
    private String permissions;
    private String status;
    private String created_at;
    private String updated_at;
    private String deleted_at;
    private String deleted_by;
    private String created_by;
    private String updated_by;
    private String last_login_attempt_time;
    private List<UserRoleDTO> userRoles;

    public List<UserRoleDTO> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRoleDTO> userRoles) {
        this.userRoles = userRoles;
        if (userRoles != null && !userRoles.isEmpty()) {
            // If there's only one role
            if (userRoles.size() == 1) {
                this.role = userRoles.get(0).getLabel();
            } else {
                // If more than one role, join the labels with a comma
                this.role = userRoles.stream()
                        .map(UserRoleDTO::getLabel)
                        .collect(Collectors.joining(", "));
            }
        }
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
