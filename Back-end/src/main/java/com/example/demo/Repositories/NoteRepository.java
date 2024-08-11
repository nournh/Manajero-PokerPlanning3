package com.example.demo.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.demo.Entity.Note;

import java.util.List;

public interface NoteRepository extends MongoRepository<Note, Long> {

    @Query("select n from Note n where n.userStory.id = :id")
    List<Note> findByUserStoryId(@Param("id") Long id);

    long countByUserStoryId(Long id);
    List<Note> findByIdUser(Long userId);

    List<Note> findByUserStoryIdAndIdUser(long userStoryId, long userId);
}