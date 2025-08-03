package com.loplyy.subscriber_service.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Table(name = "Subscriber")
public class Subscriber {

    @Id
    private long id;

    @Column("account_id")
    private long accountId;

    private UUID uuid;

    private String firstName;

    private String lastName;

    public Subscriber() {
    }

    public Subscriber(long id, long accountId, UUID uuid, String firstName, String lastName) {
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
