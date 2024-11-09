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
import lombok.NonNull;
import java.util.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NonNull
    private String title;

    private int releaseYear;
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
        
    // Перегрузка hashCode и equals
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
