package com.loplyy.subscriber_service.dto.request.playlist;

import org.springframework.data.relational.core.mapping.Column;

public class CreatePlaylistRequest {
    private long subscriberId;
    private boolean isPublic;

    public CreatePlaylistRequest() {
    }

    public CreatePlaylistRequest(long subscriberId, boolean isPublic) {
        this.subscriberId = subscriberId;
        this.isPublic = isPublic;
    }

    public long getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(long subscriberId) {
        this.subscriberId = subscriberId;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }
}

