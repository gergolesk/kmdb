package com.kood.kmdb.controller;

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

import com.kood.kmdb.model.Actor;
import com.kood.kmdb.service.ActorService;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/actors")
@AllArgsConstructor
public class ActorController {
    private ActorService actorService;

    @PostMapping
    public ResponseEntity<Actor> createActor(@RequestBody Actor actor){
        return ResponseEntity.status(HttpStatus.CREATED).body(actorService.createActor(actor));
    }

    //@GetMapping
    public ResponseEntity<List<Actor>> getAllActors() {
        return ResponseEntity.ok(actorService.getAllActors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Actor> getActorById(@PathVariable Long id) {
        return ResponseEntity.ok(actorService.getActorById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Actor> updateActor(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Actor updateActor = actorService.updateActor(id, updates);
        return ResponseEntity.ok(updateActor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteActor(@PathVariable Long id, @RequestParam(defaultValue = "false") boolean force) {
        String result = actorService.deleteActor(id, force);
        if (result.startsWith("Unable to delete")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }
    
    @GetMapping
    public ResponseEntity<List<Actor>> getActorByName(@RequestParam(required = false) String name) {
        List<Actor> actors;
        if (name!=null) {
            actors = actorService.getActorsByName(name);
        } else {
            actors = actorService.getAllActors();
        }
        return ResponseEntity.ok(actors);
    }
}
