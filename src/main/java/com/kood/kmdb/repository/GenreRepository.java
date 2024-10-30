package com.kood.kmdb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kood.kmdb.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
 
} 
