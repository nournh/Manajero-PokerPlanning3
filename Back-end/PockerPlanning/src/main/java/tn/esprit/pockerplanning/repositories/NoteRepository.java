package tn.esprit.pockerplanning.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import tn.esprit.pockerplanning.entities.Note;

import java.util.List;

public interface NoteRepository extends MongoRepository<Note, String> {

    List<Note> findByUserStoryId(String id);

    long countByUserStoryId(Long id);
    List<Note> findByIdUser(Long userId);

    List<Note> findByUserStoryIdAndIdUser(long userStoryId, long userId);
}