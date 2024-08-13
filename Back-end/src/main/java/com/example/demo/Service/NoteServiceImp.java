package com.example.demo.Service;



import com.example.demo.Entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.demo.Entity.Note;

import  com.example.demo.Entity.UserStory;
import com.example.demo.Repositories.NoteRepository;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.Repositories.UserStoryRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class NoteServiceImp implements NoteService {
    public final NoteRepository noteRepository;
    public final UserStoryRepository userStoryRepository;
    private final UserRepository userRepo;

    @Override
    public Note FindNoteById(long id) {
        return noteRepository.findById(id).orElse(null);
    }

    @Override
    public Note AddNoteAssignNoteToUserStory(Note note, long idUserStory) {
        UserStory userStory = userStoryRepository.findById(idUserStory).orElse(null);
        note.setUserStory(userStory);

        note.setDate(LocalDate.now());

        return noteRepository.save(note);
    }

    @Override
    @Transactional
    public List<Note> findNoteByUserStory(long idUserStory) {
        return noteRepository.findByUserStoryId(idUserStory);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getUserStoryWithNotes(Long userStoryId) {
        try {
            // Récupérer l'histoire utilisateur correspondant à l'ID donné
            Optional<UserStory> userStoryOptional = userStoryRepository.findById(userStoryId);
            if (!userStoryOptional.isPresent()) {
                return ResponseEntity.notFound().build(); // L'histoire utilisateur n'existe pas
            }
            UserStory userStory = userStoryOptional.get();

            // Construire les détails de l'histoire utilisateur
            Map<String, Object> response = new HashMap<>();

            // Récupérer les notes associées à l'histoire utilisateur
            List<Note> notes = noteRepository.findByUserStoryId(Long.valueOf(userStory.getId()));
            List<Map<String, Object>> notesList = new ArrayList<>();
            for (Note note : notes) {
                Map<String, Object> noteDetails = new HashMap<>();
                noteDetails.put("id", note.getId());
                noteDetails.put("description", note.getDescription());
                noteDetails.put("date", note.getDate());

                // Récupérer l'utilisateur associé à la note
                User user;
                user = userRepo.findById(String.valueOf((note.getIdUser()))).orElse(null);
                if (user != null) {
                    noteDetails.put("FirstName", user.getFirstname());
                    noteDetails.put("LastName", user.getLastname());
                } else {
                    noteDetails.put("FirstName", "Unknown");
                    noteDetails.put("LastName", "User");
                }

                notesList.add(noteDetails);
            }
            response.put("Notes", notesList);

            // Renvoyer les détails de l'histoire utilisateur avec les notes associées
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Loguer l'exception
            System.err.println("Error retrieving user story with notes: " + e.getMessage());
            // Renvoyer une réponse d'erreur
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Error retrieving user story with notes. Please try again later."));
        }
    }


    @Override
    public long NbNoteByUserStory(long id) {
        return noteRepository.countByUserStoryId(id);
    }
@Override
    public long getUserStoryIdForNote(Long noteId) {
        Note note = noteRepository.findById(noteId).orElse(null);
        if (note != null) {
            return note.getUserStory().getId();
        } else {
            // Gérer le cas où la note n'est pas trouvée
            return noteId;
        }
    }

    @Override
    public void DeleteNoteOfUserStory(Long id){

    noteRepository.deleteById(id);
    }


    @Override
    public List <Note> findNoteByIdUser(long idUser){
        return noteRepository.findByIdUser(idUser);
    }
    @Override
    public List<Note> findNoteByUserStory(long idUserStory, long idUser) {
        return noteRepository.findByUserStoryIdAndIdUser(idUserStory, idUser);
    }




}
