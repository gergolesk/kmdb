package com.kood.kmdb.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.kood.kmdb.repository.*;
import com.kood.kmdb.exceptions.ResourceNotFoundException;
import com.kood.kmdb.model.Actor;
import com.kood.kmdb.model.Genre;
import com.kood.kmdb.model.Movie;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;
    
    @Autowired
    private GenreRepository genreRepository;
    
    @Autowired
    private ActorRepository actorRepository;


    public Movie createMovie(Movie movie) {
        /*
        // Get genre  by ID
        //Genre genre = genreRepository.findById(movieRequest.getGenreId())
        //        .orElseThrow(() -> new ResourceNotFoundException("Genre not found with id: " + movieRequest.getGenreId()));
        List<Genre> genres = genreRepository.findAllById(movieRequest.getGenreId());

        // Get actors by ID
        List<Actor> actors = actorRepository.findAllById(movieRequest.getActorsIds());
          
        // Create
        Movie movie = new Movie();
        movie.setTitle(movieRequest.getTitle());
        movie.setReleaseYear(movieRequest.getReleaseYear());
        movie.setDuration(movieRequest.getDuration());
        movie.setGenre(genres);
        movie.setActors(actors);
        */

        Set<Genre> genres = movie.getGenres().stream()
                            .map(genre -> genreRepository.findById(genre.getId()).orElseThrow(() -> new ResourceNotFoundException("Genre not found with ID: " + genre.getId())))
                            .collect(Collectors.toSet());
        movie.setGenres(genres);
        
    

        Set<Actor> actors = movie.getActors().stream()
                            .map(actor -> actorRepository.findById(actor.getId()).orElseThrow(() -> new ResourceNotFoundException("Actor not found with ID: " + actor.getId())))
                            .collect(Collectors.toSet());
        movie.setActors(actors);

        // Save movie
        return movieRepository.save(movie);
    }

    public Page<Movie> getAllMovies(Pageable pageable) {
        return movieRepository.findAll(pageable);
    }

    public Movie getMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with ID: " + id));   
    }

    public Movie updateMovie(Long id, Movie updatedMovie) {
        Movie existingMovie = getMovieById(id);
        
        existingMovie.setTitle(updatedMovie.getTitle());
        existingMovie.setReleaseYear(updatedMovie.getReleaseYear());
        existingMovie.setDuration(updatedMovie.getDuration());

        // Update genres
        Set<Genre> genres = updatedMovie.getGenres().stream()
                            .map(genre -> genreRepository.findById(genre.getId())
                            .orElseThrow(() -> new ResourceNotFoundException("Genre not found with ID: " + genre.getId())))
                            .collect(Collectors.toSet());
        existingMovie.setGenres(genres);

        // Update actors
        Set<Actor> actors = updatedMovie.getActors().stream()
                            .map(actor -> actorRepository.findById(actor.getId())
                            .orElseThrow(() -> new ResourceNotFoundException("Actor not found with ID: " + actor.getId())))
                            .collect(Collectors.toSet());
        existingMovie.setActors(actors);

        return movieRepository.save(existingMovie);

    }

    public void deleteMovie(Long id) {
        if (!movieRepository.existsById(id)) {
            throw new ResourceNotFoundException("Movie not found with ID: " + id);
        }
        movieRepository.deleteById(id);
    }

    public Page<Movie> getMoviesByGenre(Long genreId,  Pageable pageable){
        return movieRepository.findByGenresId(genreId, pageable);
    }

    public Page<Movie> getMoviesByYear(int releaseYear,  Pageable pageable) {
        return movieRepository.findByReleaseYear(releaseYear, pageable);
    }

    public Page<Movie> getMoviesByActor(Long actorId,  Pageable pageable) {
        return movieRepository.findByActorsId(actorId, pageable);
    }


    public List<Actor> getActorsByMovie(Long movieId) {
        Movie movie = getMovieById(movieId);
        List<Actor> result = new ArrayList<Actor>();
        result.addAll(movie.getActors());
        return result;
    }

    public Page<Movie> getMoviesByTitle(String title, Pageable pageable) {
        return movieRepository.findByTitleContainingIgnoreCase(title, pageable);
    }

    public boolean existsMovie(Long id) {
        return movieRepository.existsById(id);
    }

    public boolean existsGenreById(Long genreId) {
        return genreRepository.existsById(genreId);
    }
    
    public boolean existsActorById(Long actorId) {
        return actorRepository.existsById(actorId);
    }

}
