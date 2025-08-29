package com.lopply.music_service.dto.response.music;

public class GetMusicWithArtist {
    private String aristUuid ;
    private String name;
    private String bio;
    private String country;
    private long musicId;
    private String musicUuid;
    private String title;
    private Long genreId;
    private Integer duration; // saniye
    private String language;

    public GetMusicWithArtist() {
    }

    public GetMusicWithArtist(String aristUuid, String name, String bio, String country, long musicId, String musicUuid, String title, Long genreId, Integer duration, String language) {
        this.aristUuid = aristUuid;
        this.name = name;
        this.bio = bio;
        this.country = country;
        this.musicId = musicId;
        this.musicUuid = musicUuid;
        this.title = title;
        this.genreId = genreId;
        this.duration = duration;
        this.language = language;
    }

    public long getMusicId() {
        return musicId;
    }

    public void setMusicId(long musicId) {
        this.musicId = musicId;
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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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
