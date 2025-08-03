package com.loplyy.subscriber_service.dto.response.subscriber;


import java.util.UUID;

public class GetSubscriberIdByUId {

    private long id;

    private long accountId;

    private UUID uuid;

    private String firstName;

    private String lastName;

    public GetSubscriberIdByUId() {
    }

    public GetSubscriberIdByUId(long id, long accountId, UUID uuid, String firstName, String lastName) {
        this.id = id;
        this.accountId = accountId;
        this.uuid = uuid;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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
}
