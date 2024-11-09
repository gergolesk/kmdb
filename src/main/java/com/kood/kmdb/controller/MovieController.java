package com.kood.kmdb.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kood.kmdb.DTO.MovieDTO;
import com.kood.kmdb.exceptions.BadRequestException;
import com.kood.kmdb.exceptions.ResourceNotFoundException;
import com.kood.kmdb.model.Actor;
import com.kood.kmdb.model.Genre;
import com.kood.kmdb.model.Movie;
import com.kood.kmdb.service.MovieService;
import java.util.List;
import java.util.stream.Collectors;

import java.util.Random;

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

 
    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable Long id) {
        return ResponseEntity.ok(convertToDTO(movieService.getMovieById(id)));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }
   
    @GetMapping
    public ResponseEntity<?> getMovies(
            @RequestParam(required = false) Long genre,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Long actor,
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "false") boolean random) {

        if (page < 0 || size < 0) {
            throw new BadRequestException(title);
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<MovieDTO> movies;
    
        if (genre!=null) {
            if (!movieService.existsGenreById(genre)) {
                throw new ResourceNotFoundException("Genre with ID " + genre + " not found");
            }
            movies = movieService.getMoviesByGenre(genre, pageable)
                    .map(this::convertToDTO);
        } else if (year!=null) {
            movies = movieService.getMoviesByYear(year, pageable)
                    .map(this::convertToDTO);
        } else if (actor!=null) {
            if (!movieService.existsActorById(actor)) {
                throw new ResourceNotFoundException("Actor with ID " + actor + " not found");
            }
            movies = movieService.getMoviesByActor(actor, pageable)
                    .map(this::convertToDTO);
        }  else if (title!=null) {
            movies = movieService.getMoviesByTitle(title, pageable)
                    .map(this::convertToDTO);
        } else {
            movies = movieService.getAllMovies(pageable)
                    .map(this::convertToDTO);
        }
        if (movies.isEmpty()) {
            throw new ResourceNotFoundException("No movies found");
        } 
        if (random) {
            Random randomizer = new Random();
            List<MovieDTO> movieList = movies.getContent();
            MovieDTO randomMovieDTO = movieList.get(randomizer.nextInt(movieList.size()));
            return ResponseEntity.ok(randomMovieDTO);
        }
    
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{id}/actors")
    public ResponseEntity<List<String>> getActorsInMovie(@PathVariable Long id) {
        
        if (!movieService.existsMovie(id)) {
            throw new ResourceNotFoundException("Movie with ID " + id + " not found");
        } 
        MovieDTO moviedto;
        moviedto = convertToDTO(movieService.getMovieById(id));
        return ResponseEntity.ok(moviedto.getActors());
    }


    @PatchMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie movie) {
        return ResponseEntity.ok(movieService.updateMovie(id, movie));
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