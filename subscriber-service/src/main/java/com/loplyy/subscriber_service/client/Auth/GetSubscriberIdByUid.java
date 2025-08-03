package com.loplyy.subscriber_service.client.Auth;

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
