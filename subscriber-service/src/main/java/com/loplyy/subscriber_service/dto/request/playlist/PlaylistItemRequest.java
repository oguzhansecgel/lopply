package com.loplyy.subscriber_service.dto.request.playlist;

public class PlaylistItemRequest {
    private String playlist_uid;
    private String music_uid;

    public PlaylistItemRequest(String music_uid, String playlist_uid) {
        this.music_uid = music_uid;
        this.playlist_uid = playlist_uid;
    }

    public PlaylistItemRequest() {
    }

    public String getPlaylist_uid() {
        return playlist_uid;
    }

    public void setPlaylist_uid(String playlist_uid) {
        this.playlist_uid = playlist_uid;
    }

    public String getMusic_uid() {
        return music_uid;
    }

    public void setMusic_uid(String music_uid) {
        this.music_uid = music_uid;
    }
}
