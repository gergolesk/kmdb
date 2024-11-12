package com.kood.kmdb.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.*;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Title cannot be null")
    @Size(min = 1, message = "Title cannot be empty")
    private String title;

    @Positive(message = "Release year must be a positive number")
    private int releaseYear;

    @Positive(message = "Duration must be a positive number")
    private int duration;

    @ManyToMany
    @JoinTable(name = "movie_genre",
                joinColumns = @JoinColumn(name = "movie_id"),
                inverseJoinColumns = @JoinColumn(name = "genre_id"))
    //@JsonManagedReference
    private Set<Genre> genres = new HashSet<>();                

    @ManyToMany
    @JoinTable(name = "movie_actor", 
                joinColumns = @JoinColumn(name = "movie_id"),
                inverseJoinColumns = @JoinColumn(name = "actor_id"))
    //@JsonManagedReference
    private Set<Actor> actors = new HashSet<>();
 
    public void setGenre(Set<Genre> genres) {
        this.genres = genres;
    }

    public void setActors(Set<Actor> actors) {
        this.actors = actors;
    } 
        
    // Reload hashCode и equals
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Movie movie = (Movie) obj;
        return id != null && id.equals(movie.id);
    }
}
