package com.lopply.music_service.dto.request.album;

import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDate;
import java.util.UUID;

public class CreateAlbumRequest {
    private String title;
    @Column("artist_id")
    private Long artistId;
    @Column("release_date")
    private LocalDate releaseDate;

    public CreateAlbumRequest() {
    }

    public CreateAlbumRequest(String title, Long artistId, LocalDate releaseDate) {
        this.title = title;
        this.artistId = artistId;
        this.releaseDate = releaseDate;
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
