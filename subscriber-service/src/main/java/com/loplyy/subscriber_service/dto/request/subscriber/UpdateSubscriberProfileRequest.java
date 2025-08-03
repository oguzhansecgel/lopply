package com.loplyy.subscriber_service.dto.request.subscriber;

import java.util.UUID;

public class UpdateSubscriberProfileRequest {
    private UUID uid;

    private String firstName;

    private String lastName;

    public UpdateSubscriberProfileRequest() {
    }

    public UpdateSubscriberProfileRequest(UUID uid, String firstName, String lastName) {
        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
