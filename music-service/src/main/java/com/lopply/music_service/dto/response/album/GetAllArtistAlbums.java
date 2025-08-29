package com.lopply.music_service.dto.response.album;

import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDate;
import java.util.UUID;

public class GetAllArtistAlbums {
    private String albumUuid;
    private String albumTitle;
    private LocalDate releaseDate;
    private String artistUuid;
    private String aritstName;

    public GetAllArtistAlbums() {
    }

    public GetAllArtistAlbums(String albumUuid, String albumTitle, LocalDate releaseDate, String artistUuid, String aritstName) {
        this.albumUuid = albumUuid;
        this.albumTitle = albumTitle;
        this.releaseDate = releaseDate;
        this.artistUuid = artistUuid;
        this.aritstName = aritstName;
    }

    public String getAlbumUuid() {
        return albumUuid;
    }

    public void setAlbumUuid(String albumUuid) {
        this.albumUuid = albumUuid;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getArtistUuid() {
        return artistUuid;
    }

    public void setArtistUuid(String artistUuid) {
        this.artistUuid = artistUuid;
    }

    public String getAritstName() {
        return aritstName;
    }

    public void setAritstName(String aritstName) {
        this.aritstName = aritstName;
    }
}
