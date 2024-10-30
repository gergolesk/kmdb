package com.kood.kmdb.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.*;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Genre {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @ManyToMany(mappedBy = "genres")
    @JsonIgnore
    private Set<Movie> movies = new HashSet<>();
}
