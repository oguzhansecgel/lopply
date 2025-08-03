package com.lopply.auth_service.dto;

public class GetSubscriberIdByUid {
    private long id;

    public GetSubscriberIdByUid() {
    }

    public GetSubscriberIdByUid(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}