package com.example.demo.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.example.demo.Entity.Note;
import com.example.demo.Repositories.NoteRepository;
import com.example.demo.Service.IUserStoryServiceImp;
import com.example.demo.Service.NoteService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")

@RequestMapping("note")
public class NoteController {
    public  final NoteService noteService;
    public final IUserStoryServiceImp userStoryService;
    public final NoteRepository noteRepository;
    @PostMapping("/AddNoteAssignNoteToUserStory/{idUserStory}")//affectation de note
    // a us de la part de developpeur  //utilser
    public Note AddNoteAssignNoteToUserStory(@RequestBody Note note,@PathVariable long idUserStory){

        return noteService.AddNoteAssignNoteToUserStory(note,idUserStory);
    }
    @GetMapping("/findNoteByUserStory/{idUserStory}")//pour le chef de projet momken manest3mlhech
    //non utiliser
    public List<Note> findNoteByUserStory(@PathVariable  long idUserStory) {
        return noteService.findNoteByUserStory(idUserStory);
    }
    @GetMapping("/NbNoteByUserStory/{id}")// pour statistique
    public long NbNoteByUserStory(@PathVariable long id) {
        return  noteService.NbNoteByUserStory(id);
    }


    @DeleteMapping("/DeleteNoteOfUserStory/{id}")//delete de la part de developpeur
    //utiliser
   public  void DeleteNoteOfUserStory(@PathVariable Long id){
         noteService.DeleteNoteOfUserStory(id);    }
    @GetMapping ("/findNoteByIdUser/{idUser}")// kol developpeur afficher les notes mte3ou
    //non utiliser
    public List <Note> findNoteByIdUser(@PathVariable long idUser){
        return noteService.findNoteByIdUser(idUser);
    }
    @GetMapping ("/getUserStoryWithNotes/{userStoryId}")//momken manest3mlhech
    // les us avec les notes specifie//utilise
   public  ResponseEntity<Map<String, Object>> getUserStoryWithNotes(@PathVariable Long userStoryId){
       return  noteService.getUserStoryWithNotes(userStoryId);

   }
    @GetMapping ("/findNoteByUserStory/{idUserStory}/{idUser}")//st3mltha lil developpeur
    //utilise
  public  List<Note> findNoteByUserStory(@PathVariable long idUserStory, @PathVariable long idUser){
        return noteService.findNoteByUserStory(idUserStory,idUser);
  }
  @GetMapping("/FindNoteById/{id}")// lil update
  //utilise
  public Note FindNoteById(@PathVariable long id){
        return noteService.FindNoteById(id);
  }
  @GetMapping("/getUserStoryIdForNote/{noteId}") // l kol note yatini id ta3 us
  //utilise
  public long getUserStoryIdForNote(@PathVariable long noteId){
        return  noteService.getUserStoryIdForNote(noteId);
}
    @PutMapping("/updateNote/{idNote}")
    public ResponseEntity<Note> updateNote(@PathVariable long idNote, @RequestBody Note noteDetails) {
        Note note = noteRepository.findById(idNote)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found with id: " + idNote));

        // Mettre à jour les détails de la note
        note.setDescription(noteDetails.getDescription());

        // Enregistrer la note mise à jour dans la base de données
        Note updatedNote = noteRepository.save(note);

        return ResponseEntity.ok(updatedNote);
    }


}

