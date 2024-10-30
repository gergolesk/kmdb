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

import com.kood.kmdb.model.Movie;
import com.kood.kmdb.service.MovieService;
import java.util.List;

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

    //@GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }
    /* 
    @GetMapping("?genre={genreId}")
    public ResponseEntity<List<Movie>> getMovieByGenre(@RequestParam Long genreId){
        List<Movie> movies = movieService.getMoviesByGenre(genreId);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("?year={year}")
    public ResponseEntity<List<Movie>> getMovieByYear(@RequestParam int year){
        List<Movie> movies = movieService.getMoviesByYear(year);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("?actor={actorId}")
    public ResponseEntity<List<Movie>> getMovieByActor(@RequestParam Long actorId){
        List<Movie> movies = movieService.getMoviesByActor(actorId);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{movieId}/actors")
    public ResponseEntity<List<Actor>> getActorsByMovie(@RequestParam Long movieId){
        List<Actor> actors = movieService.getActorsByMovie(movieId);
        return ResponseEntity.ok(actors);
    }
    */

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
}
