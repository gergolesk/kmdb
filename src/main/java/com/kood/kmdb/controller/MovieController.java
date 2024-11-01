package com.kood.kmdb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kood.kmdb.DTO.MovieDTO;
import com.kood.kmdb.model.Actor;
import com.kood.kmdb.model.Genre;
import com.kood.kmdb.model.Movie;
import com.kood.kmdb.service.MovieService;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        Movie createdMovie = movieService.createMovie(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMovie);
    }

    /*
    //@GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }
    */

    
    /*
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.getMovieById(id));
    }
    */

    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable Long id) {
        return ResponseEntity.ok(convertToDTO(movieService.getMovieById(id)));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }
    /*
    @GetMapping
    public ResponseEntity<List<Movie>> getMovies(
            @RequestParam(required = false) Long genre,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Long actor) {
        List<Movie> movies;
        if (genre!=null){
            movies = movieService.getMoviesByGenre(genre);
        } else if (year!=null) {
            movies = movieService.getMoviesByYear(year);
        } else if (actor!=null) {
            movies = movieService.getMoviesByActor(actor);
        }  else {
            movies = movieService.getAllMovies();
        }

        return ResponseEntity.ok(movies);
    }   
    */
    @GetMapping
    public ResponseEntity<List<MovieDTO>> getMovies(
            @RequestParam(required = false) Long genre,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Long actor) {
        List<MovieDTO> movies;
    
        if (genre!=null) {
            movies = movieService.getMoviesByGenre(genre).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } else if (year!=null) {
            movies = movieService.getMoviesByYear(year).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } else if (actor!=null) {
            movies = movieService.getMoviesByActor(actor).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } else {
            movies = movieService.getAllMovies().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
    
        return ResponseEntity.ok(movies);
    }


    private MovieDTO convertToDTO(Movie movie) {
        MovieDTO dto = new MovieDTO();
        dto.setId(movie.getId());
        dto.setTitle(movie.getTitle());
        dto.setReleaseYear(movie.getReleaseYear());
        dto.setDuration(movie.getDuration());
        dto.setGenres(movie.getGenres().stream().map(Genre::getName).collect(Collectors.toList()));
        dto.setActors(movie.getActors().stream().map(Actor::getName).collect(Collectors.toList()));
        return dto;
    }
}
