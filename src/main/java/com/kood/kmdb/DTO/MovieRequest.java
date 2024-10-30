package com.kood.kmdb.DTO;

import java.util.*;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MovieRequest {
    private String title;
    private int releaseYear;
    private int duration;
    private List<Long> genreId;
    private List<Long> actorIds;

    public String getTitle(){
        return title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public int getDuration() {
        return duration;
    }

    public List<Long> getGenreId() {
        return genreId;
    }
    
    public List<Long> getActorsIds() {
        return actorIds;
    }
}
