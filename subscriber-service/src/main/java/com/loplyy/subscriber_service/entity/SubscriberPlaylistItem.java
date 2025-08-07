package com.loplyy.subscriber_service.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table(name = "Subscriber_PlaylistItem")
public class SubscriberPlaylistItem {

    @Id
    private long id;

    @Column("uuid")
    private UUID uid;

    @Column("music_id")
    private long musicId;

    @Column("playlist_id")
    private long playlistId;

    public SubscriberPlaylistItem() {
    }

    public SubscriberPlaylistItem(long id, UUID uid, long musicId, long playlistId) {
        this.id = id;
        this.uid = uid;
        this.musicId = musicId;
        this.playlistId = playlistId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public long getMusicId() {
        return musicId;
    }

    public void setMusicId(long musicId) {
        this.musicId = musicId;
    }

    public long getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(long playlistId) {
        this.playlistId = playlistId;
    }
}
