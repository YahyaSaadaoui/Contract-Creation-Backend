package com.hps.admindashboardservice.events;

public class UserDeletedEvent {
    private long userId;

    public UserDeletedEvent() {}

    public UserDeletedEvent(long userId) {
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
