package com.kood.kmdb.repository;

import java.util.*;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kood.kmdb.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long>{
    @EntityGraph(attributePaths = {"genres", "actors"})
    List<Movie> findByGenresId(Long genreId);

    @EntityGraph(attributePaths = {"genres", "actors"})
    List<Movie> findByReleaseYear(int releaseYear);

    @EntityGraph(attributePaths = {"genres", "actors"})
    List<Movie> findByActorsId(Long actorId);

    @EntityGraph(attributePaths = {"genres", "actors"})
    List<Movie> findByTitle(String title);
}
