package com.lopply.music_service.dto.response.music;

public class GetAllArtistMusic {
    private String aristUuid ;
    private String name;
    private long musicId;
    private String musicUuid;
    private String title;
    private Long genreId;
    private String language;

    public GetAllArtistMusic() {
    }

    public GetAllArtistMusic(String aristUuid, String name, long musicId, String musicUuid, String title, Long genreId, String language) {
        this.aristUuid = aristUuid;
        this.name = name;
        this.musicId = musicId;
        this.musicUuid = musicUuid;
        this.title = title;
        this.genreId = genreId;
        this.language = language;
    }

    public String getAristUuid() {
        return aristUuid;
    }

    public void setAristUuid(String aristUuid) {
        this.aristUuid = aristUuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getMusicId() {
        return musicId;
    }

    public void setMusicId(long musicId) {
        this.musicId = musicId;
    }

    public String getMusicUuid() {
        return musicUuid;
    }

    public void setMusicUuid(String musicUuid) {
        this.musicUuid = musicUuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
