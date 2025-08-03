package com.loplyy.subscriber_service.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table(name = "Subscriber_Playlist")
public class SubscriberPlaylist {

    @Id
    private long id;
    private UUID uuid;
    @Column("music_id")
    private int musicId;
    @Column("subscriber_id")
    private long subscriberId;
    @Column("is_public")
    private boolean isPublic;

    public SubscriberPlaylist() {
    }

    public SubscriberPlaylist(long id, UUID uuid, int musicId, long subscriberId, boolean isPublic) {
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
        this.uuid = UUID.randomUUID();
    }

    public int getMusicId() {
        return musicId;
    }

    public void setMusicId(int musicId) {
        this.musicId = musicId;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public long getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(long subscriberId) {
        this.subscriberId = subscriberId;
    }
}
