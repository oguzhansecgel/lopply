package com.lopply.music_service.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("artist")
public class Artist {
    @Id
    private Long id;
    private UUID uuid = UUID.randomUUID();
    private String name;
    private String bio;
    private String country;

    public Artist() {
    }

    public Artist(Long id, UUID uuid, String name, String bio, String country) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.bio = bio;
        this.country = country;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = UUID.randomUUID();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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