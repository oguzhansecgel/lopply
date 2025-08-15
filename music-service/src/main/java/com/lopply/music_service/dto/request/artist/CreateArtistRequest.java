package com.lopply.music_service.dto.request.artist;

public class CreateArtistRequest {
    private String name;
    private String bio;
    private String country;

    public CreateArtistRequest() {
    }

    public CreateArtistRequest(String name, String bio, String country) {
        this.name = name;
        this.bio = bio;
        this.country = country;
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
}
