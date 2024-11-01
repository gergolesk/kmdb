package com.kood.kmdb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /*
    @Transactional
    public Movie createMovie(Movie movie) {
        Movie savedMovie = movieRepository.save(movie);
        
        //Add movie for each actor
        for (Actor actor : movie.getActors()){
            actor.getMovies().add(movie);
            actorRepository.save(actor);
            
        }
        
        //Add movie for each genre
        for (Genre genre : movie.getGenres()){
            genre.getMovies().add(movie);
        }
        
        return savedMovie;
    }
    */

    public Movie createMovie(Movie movie) {
        /*
        // Получаем жанр по его ID
        //Genre genre = genreRepository.findById(movieRequest.getGenreId())
        //        .orElseThrow(() -> new ResourceNotFoundException("Genre not found with id: " + movieRequest.getGenreId()));
        List<Genre> genres = genreRepository.findAllById(movieRequest.getGenreId());

        // Получаем актеров по их ID
        List<Actor> actors = actorRepository.findAllById(movieRequest.getActorsIds());
          
        // Создаем новый объект фильма
        Movie movie = new Movie();
        movie.setTitle(movieRequest.getTitle());
        movie.setReleaseYear(movieRequest.getReleaseYear());
        movie.setDuration(movieRequest.getDuration());
        movie.setGenre(genres);
        movie.setActors(actors);
        */

        Set<Genre> genres = movie.getGenres().stream()
                            .map(genre -> genreRepository.findById(genre.getId()).orElseThrow())
                            .collect(Collectors.toSet());
        movie.setGenres(genres);
        
    

        Set<Actor> actors = movie.getActors().stream()
                            .map(actor -> actorRepository.findById(actor.getId()).orElseThrow())
                            .collect(Collectors.toSet());
        movie.setActors(actors);

        // Сохраняем фильм в базе данных
        return movieRepository.save(movie);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));   
    }

    public Movie updateMovie(Long id, Map<String, Object> updates) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre not found"));
        updates.forEach((key, value) -> {
            switch (key) {
                case "title":
                    movie.setTitle((String) value);
                    break;
                case "releaseYear":
                    movie.setReleaseYear((int)value);
                    break;
                case "duration":
                    movie.setDuration((int)value);
                    break;            
                default:
                    break;
            }
        }
        );
        return movieRepository.save(movie);
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

    public List<Movie> getMoviesByGenre(Long genreId){
        return movieRepository.findByGenresId(genreId);
    }

    public List<Movie> getMoviesByYear(int releaseYear) {
        return movieRepository.findByReleaseYear(releaseYear);
    }

    public List<Movie> getMoviesByActor(Long actorId) {
        return movieRepository.findByActorsId(actorId);
    }


    public List<Actor> getActorsByMovie(Long movieId) {
        Movie movie = getMovieById(movieId);
        List<Actor> result = new ArrayList<Actor>();
        result.addAll(movie.getActors());
        return result;
    }

}
