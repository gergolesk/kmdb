package com.kood.kmdb.repository;

import com.kood.kmdb.model.Actor;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends JpaRepository<Actor,Long> {

    List<Actor> findByNameContainingIgnoreCase(String name);

}
