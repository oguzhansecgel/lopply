package com.lopply.music_service.dto.request.genre;

public class CreateGenreRequest {
    private String name;

    public CreateGenreRequest() {
    }

    public CreateGenreRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
