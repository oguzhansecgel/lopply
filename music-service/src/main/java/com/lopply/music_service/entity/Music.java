package com.lopply.music_service.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("music")
public class Music {
    @Id
    private Long id;
    private String title;
    @Column("album_id")
    private Long albumId;
    @Column("artist_id")
    private Long artistId;
    @Column("genre_id")
    private Long genreId;
    private Integer duration; // saniye
    private String language;

    public Music() {
    }

    public Music(Long id, String title, Long albumId, Long artistId, Long genreId, Integer duration, String language) {
        this.id = id;
        this.title = title;
        this.albumId = albumId;
        this.artistId = artistId;
        this.genreId = genreId;
        this.duration = duration;
        this.language = language;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
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