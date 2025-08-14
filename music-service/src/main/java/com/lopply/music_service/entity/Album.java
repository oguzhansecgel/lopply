package com.lopply.music_service.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.UUID;

@Table("album")
public class Album {
    @Id
    private Long id;
    private UUID uuid;
    private String title;
    @Column("artist_id")
    private Long artistId;
    @Column("release_date")
    private LocalDate releaseDate;

    public Album() {
    }

    public Album(Long id, UUID uuid, String title, Long artistId, LocalDate releaseDate) {
        this.id = id;
        this.uuid = uuid;
        this.title = title;
        this.artistId = artistId;
        this.releaseDate = releaseDate;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}