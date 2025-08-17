package com.lopply.music_service.dto.request.music;

import org.springframework.data.relational.core.mapping.Column;

public class CreateMusicRequest {
    private String title;
    @Column("album_id")
    private String albumUId;
    @Column("artist_id")
    private String artistUId;
    @Column("genre_id")
    private String genreUId;
    private Integer duration; // saniye
    private String language;

    public CreateMusicRequest() {
    }

    public CreateMusicRequest(String title, String albumUId, String artistUId, String genreUId, Integer duration, String language) {
        this.title = title;
        this.albumUId = albumUId;
        this.artistUId = artistUId;
        this.genreUId = genreUId;
        this.duration = duration;
        this.language = language;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbumUId() {
        return albumUId;
    }

    public void setAlbumUId(String albumUId) {
        this.albumUId = albumUId;
    }

    public String getArtistUId() {
        return artistUId;
    }

    public void setArtistUId(String artistUId) {
        this.artistUId = artistUId;
    }

    public String getGenreUId() {
        return genreUId;
    }

    public void setGenreUId(String genreUId) {
        this.genreUId = genreUId;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
