package com.loplyy.subscriber_service.dto.request.subscriber;

import java.util.UUID;

public class CreateSubscriberProfileRequest {

    private String firstName;

    private String lastName;

    private UUID accountUId;

    public CreateSubscriberProfileRequest() {
    }

    public CreateSubscriberProfileRequest(String firstName, String lastName, UUID accountUId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountUId = accountUId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UUID getAccountUId() {
        return accountUId;
    }

    public void setAccountUId(UUID accountUId) {
        this.accountUId = accountUId;
    }
}
