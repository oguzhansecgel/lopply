package com.loplyy.subscriber_service.dto.response.playlistItem;

public class GetPlaylistItem {
    private long id;
    private String music_uid;
    private long playlist_id;
    private String title;
    private String author;
    public GetPlaylistItem() {
    }

    public GetPlaylistItem(long id, String music_uid, long playlist_id, String title, String author) {
        this.id = id;
        this.music_uid = music_uid;
        this.playlist_id = playlist_id;
        this.title = title;
        this.author = author;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMusic_uid() {
        return music_uid;
    }

    public void setMusic_uid(String music_uid) {
        this.music_uid = music_uid;
    }

    public long getPlaylist_id() {
        return playlist_id;
    }

    public void setPlaylist_id(long playlist_id) {
        this.playlist_id = playlist_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
