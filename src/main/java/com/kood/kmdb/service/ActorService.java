package com.kood.kmdb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kood.kmdb.repository.ActorRepository;
import com.kood.kmdb.exceptions.ResourceNotFoundException;
import com.kood.kmdb.model.Actor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ActorService {
    @Autowired
    private ActorRepository actorRepository;

    public Actor createActor(Actor actor) {
        return actorRepository.save(actor);
    }

    public List<Actor> getAllActors() {
        return actorRepository.findAll();
    }

    public Actor getActorById(Long id) {
        return actorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Actor not found"));
    }

    public Actor updateActor(Long id, Map<String, Object> updates) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre not found"));
        updates.forEach((key, value) -> {
            switch (key) {
                case "name":
                    actor.setName((String) value);
                    break;
                case "birthDate":
                    actor.setBirthDate(LocalDate.parse((String) value));
                    break;            
                default:
                    break;
            }
        }
        );
        return actorRepository.save(actor);
    }

    public String deleteActor(Long id, boolean force) {
        //actorRepository.deleteById(id);
        Optional<Actor> actorOptional = actorRepository.findById(id);

        if (actorOptional.isPresent()) {
            Actor actor = actorOptional.get();

            // Проверяем наличие связанных фильмов
            if (!actor.getMovies().isEmpty()) {
                if (!force) {
                    // Отмена удаления, если force = false
                    return "Unable to delete actor '" + actor.getName() +
                           "' as they are associated with " + actor.getMovies().size() + " movies";
                } else {
                    // Удаляем связи, если force = true
                    actor.getMovies().forEach(movie -> movie.getActors().remove(actor));
                    actorRepository.delete(actor);
                    return "Actor '" + actor.getName() + "' and all their relationships were deleted successfully";
                }
            } else {
                actorRepository.delete(actor);
                return "Actor '" + actor.getName() + "' was deleted successfully";
            }
        } else {
            return "Actor not found";
        }
    }

    public List<Actor> getActorsByName(String name) {
        return actorRepository.findByNameContainingIgnoreCase(name);
    }

}
