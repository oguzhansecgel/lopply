package com.loplyy.subscriber_service.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table(name = "Subscriber_Playlist")
public class Playlist {

    @Id
    private long id;
    private UUID uuid;
    private int musicId;

    private boolean isPublic;

    public Playlist() {
    }

    public Playlist(long id, UUID uuid, int musicId, boolean isPublic) {
        this.id = id;
        this.uuid = uuid;
        this.musicId = musicId;
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

}
