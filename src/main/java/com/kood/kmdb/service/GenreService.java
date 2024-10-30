package com.kood.kmdb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kood.kmdb.exceptions.ResourceNotFoundException;
import com.kood.kmdb.model.Genre;
import com.kood.kmdb.repository.GenreRepository;

import java.util.*;

@Service
public class GenreService {
    @Autowired
    private GenreRepository genreRepository;

    public Genre creatGenre(Genre genre){
        return genreRepository.save(genre);
    }

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public Genre getGenreById(Long id) {        
        return genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre not found"));                
    }

    public String deleteById(Long id, boolean force) {
        //genreRepository.deleteById(id);

        Optional<Genre> genreOptional = genreRepository.findById(id);
        
        if (genreOptional.isPresent()) {
            Genre genre = genreOptional.get();

            // Проверяем наличие связанных фильмов
            if (!genre.getMovies().isEmpty()) {
                if (!force) {
                    // Отмена удаления, если force = false
                    return "Cannot delete genre '" + genre.getName() +
                           "' because it has " + genre.getMovies().size() + " associated movies";
                } else {
                    // Удаляем связи, если force = true
                    genre.getMovies().forEach(movie -> movie.getGenres().remove(genre));
                    genreRepository.delete(genre);
                    return "Genre '" + genre.getName() + "' and all its relationships were deleted successfully";
                }
            } else {
                genreRepository.delete(genre);
                return "Genre '" + genre.getName() + "' was deleted successfully";
            }
        } else {
            return "Genre not found";
        }
    }

    public Genre updateGenre(Long id, Map<String, Object> updates) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre not found"));
        updates.forEach((key, value) -> {
            switch (key) {
                case "name":
                    genre.setName((String) value);
                    break;
            
                default:
                    break;
            }
        }
        );
        return genreRepository.save(genre);
    }

}