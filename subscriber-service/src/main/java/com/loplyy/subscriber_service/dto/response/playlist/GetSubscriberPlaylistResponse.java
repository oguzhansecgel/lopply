package com.loplyy.subscriber_service.dto.response.playlist;

import java.util.UUID;

public class GetSubscriberPlaylistResponse {
    private long id;
    private UUID uuid;
    private int musicId;
    private long subscriberId;
    private boolean isPublic;

    public GetSubscriberPlaylistResponse() {
    }

    public GetSubscriberPlaylistResponse(long id, UUID uuid, int musicId, long subscriberId, boolean isPublic) {
        this.id = id;
        this.uuid = uuid;
        this.musicId = musicId;
        this.subscriberId = subscriberId;
        this.isPublic = isPublic;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public int getMusicId() {
        return musicId;
    }

    public void setMusicId(int musicId) {
        this.musicId = musicId;
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
