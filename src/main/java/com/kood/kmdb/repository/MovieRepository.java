package com.kood.kmdb.repository;

import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kood.kmdb.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long>{
    @EntityGraph(attributePaths = {"genres", "actors"})
    Page<Movie> findByGenresId(Long genreId, Pageable pageable);

    @EntityGraph(attributePaths = {"genres", "actors"})
    Page<Movie> findByReleaseYear(int releaseYear, Pageable pageable);

    @EntityGraph(attributePaths = {"genres", "actors"})
    Page<Movie> findByActorsId(Long actorId,  Pageable pageable);

    @EntityGraph(attributePaths = {"genres", "actors"})
    List<Movie> findByTitle(String title);

    Page<Movie> findByTitleContainingIgnoreCase(String name, Pageable pageable);
}
