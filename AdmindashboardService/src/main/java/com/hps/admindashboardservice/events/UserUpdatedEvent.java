package com.hps.admindashboardservice.events;

import com.hps.admindashboardservice.dto.UserDTO;

public class UserUpdatedEvent {
    private UserDTO userDTO;

    public UserUpdatedEvent() {}

    public UserUpdatedEvent(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
}
