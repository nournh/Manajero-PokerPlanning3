package tn.esprit.pockerplanning.services;

import org.springframework.http.ResponseEntity;
import tn.esprit.pockerplanning.entities.Note;

import java.util.List;
import java.util.Map;

public interface NoteService {
    Note FindNoteById (long id);
    Note AddNoteAssignNoteToUserStory(Note note, long idUserStory);
    List<Note> findNoteByUserStory(String idUserStory);
    long NbNoteByUserStory(long id);
    void DeleteNoteOfUserStory(Long id);
    List <Note> findNoteByIdUser(long idUser);

    ResponseEntity<Map<String, Object>> getUserStoryWithNotes(Long userStoryId);
    List<Note> findNoteByUserStory(long idUserStory, long idUser);
     String getUserStoryIdForNote(Long noteId);

}
