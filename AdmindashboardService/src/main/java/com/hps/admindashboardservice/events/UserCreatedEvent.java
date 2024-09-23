package com.hps.admindashboardservice.events;

import com.hps.admindashboardservice.dto.UserDTO;

public class UserCreatedEvent {
    private UserDTO userDTO;

    public UserCreatedEvent() {}

    public UserCreatedEvent(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
}
