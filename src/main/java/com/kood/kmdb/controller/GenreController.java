package com.kood.kmdb.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kood.kmdb.model.Genre;
import com.kood.kmdb.service.GenreService;

@RestController
@RequestMapping("/api/genres")
public class GenreController {
    @Autowired
    private GenreService genreService;

    @PostMapping
    public ResponseEntity<Genre> createGenre(@Validated @RequestBody Genre genre) {
        return ResponseEntity.status(HttpStatus.CREATED).body(genreService.creatGenre(genre));
    }

    @GetMapping
    public ResponseEntity<List<Genre>> getAllGenres(){
        return ResponseEntity.ok(genreService.getAllGenres());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genre> getGenreById(@PathVariable Long id) {
        return ResponseEntity.ok(genreService.getGenreById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Genre> updateGenre(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Genre updateGenre = genreService.updateGenre(id, updates);
        return ResponseEntity.ok(updateGenre);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGenre(@PathVariable Long id, @RequestParam(defaultValue = "false") boolean force) {
        String result = genreService.deleteById(id, force);
        if (result.startsWith("Cannot delete")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }
    
}
