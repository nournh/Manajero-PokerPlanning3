package com.example.demo.Controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Entity.Card;
import com.example.demo.Entity.Sprint;
import com.example.demo.Entity.UserStory;
import com.example.demo.Repositories.UserStoryRepository;
import com.example.demo.Service.ISprintService;
import com.example.demo.Service.IUserStoryService;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
//@CrossOrigin(origins = "*")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@RequestMapping("userStory")
public class UserStoryController {
    public final IUserStoryService iUserStoryService;
    private final UserStoryRepository userStoryRepo;
    @PostMapping("/addUSAndAssign/{id}")
    public UserStory addUSAndAssignUsToSprint(@RequestBody UserStory us,@PathVariable long id) {
        return iUserStoryService.addUSAndAssignUsToSprint(us,id);
    }
    @GetMapping("/getUSBySprintId/{id}")
    public List<UserStory> getUserStoriesBySprintId(@PathVariable Long id) {
        return iUserStoryService.getUserStoriesBySprintId(id);
    }
    @DeleteMapping("/deleteUS/{id}")
    public void deleteUserStoryById(@PathVariable Long id) {
        iUserStoryService.deleteUserStoryById(id);
    }
    @PutMapping("/updateUS")
    public UserStory updateUserStory(@RequestBody UserStory us) {
        return iUserStoryService.updateUserStory(us);
    }
    @GetMapping("/getUSbyId/{id}")
    public UserStory findById(@PathVariable long id) {
        return iUserStoryService.findById(id);
    }
    //Rihab
    @GetMapping("/ListUserStory")// pour developpeur par sprint
    public List<UserStory> getAllUserStory(){
        return iUserStoryService.FindAllUserStory();
    }
    @GetMapping("/{id}")// pour developpeur pourestimer ou noter
    public UserStory findUserStoryById(@PathVariable long id){
        return iUserStoryService.FindUserStoryById(id);
    }
    @PostMapping("/AssignCardToUserStory/{idCard}/{idUserStory}")//affectation card a us de la part de devlop
    public UserStory AssignCardToUserStory(@PathVariable long idCard,@PathVariable long idUserStory)
    {
        return iUserStoryService.AssignCardToUserStory(idCard,idUserStory);
    }

    @GetMapping ("/getCardsByUserStoryId/{userStoryId}")//dhaherli mch bech nest3mlha
    public  List<Card> getCardsByUserStoryId(@PathVariable long userStoryId){
        return iUserStoryService.getCardsByUserStoryId(userStoryId);
    }

    @GetMapping ("/getUsersWithAllUserStoriesAndCards/{idSprint}")//chef de projet yra  us avec estimation et nom de user
    public ResponseEntity<List<Map<String, Object>>> getUsersWithAllUserStoriesAndCards(@PathVariable long idSprint){
        return iUserStoryService.getUsersWithAllUserStoriesAndCards(idSprint);
    }
    @GetMapping ("/getUserIdWithAllUserStoriesAndCards/{userId}")//afficher lkol developpeur
    // userStory et les cards affecter avec leur estimation
    public ResponseEntity<List<Map<String, Object>>> getUsersWithAllUserStoriesAndCards(@PathVariable  Long userId){
        return iUserStoryService.getUsersWithAllUserStoriesAndCards(userId);
    }
    @GetMapping("/FindUsBySprint/{idSprint}")
    public List<UserStory> FindUsBySprint(@PathVariable  long idSprint) {
        return iUserStoryService.FindUsBySprint(idSprint);
    }
    @GetMapping("/getUsersWithAllUserStoriesAndnotes/{idSprint}")
    public ResponseEntity<List<Map<String, Object>>> getUsersWithAllUserStoriesAndnotes(@PathVariable long idSprint){
        return iUserStoryService.getUsersWithAllUserStoriesAndNotes(idSprint);
    }
    /*@PutMapping("/updateAssignedCard/{idCard}/{idUserStory}")
    public void updateAssignment(long userStoryId, long oldCardSetId, long newCardSetId){
       return USService.updateAssignment(userStoryId,oldCardSetId,newCardSetId);
    }*/
    @DeleteMapping("/DeleteCardFromUserStory/{userStoryId}/{cardIdLoaded}")
    public void DeleteCardFromUserStory( @PathVariable long userStoryId, @PathVariable long cardIdLoaded) {
        iUserStoryService.DeleteCardFromUserStory(userStoryId,cardIdLoaded);    }
    @PutMapping("/updateCardAssignment/{idUserStory}/{idOldCard}/{idNewCard}")
    public    UserStory updateCardAssignment(@PathVariable long idUserStory,@PathVariable  long idOldCard,@PathVariable long idNewCard) {
        return  iUserStoryService.updateCardAssignment(idUserStory, idOldCard,idNewCard);
    }

    @GetMapping("/calculateComplexityPercentageForUserStory/{userStoryId}")
    public Map<String, String> calculateComplexityPercentageForUserStory(@PathVariable int userStoryId){
        return iUserStoryService.calculateComplexityPercentageForUserStory(userStoryId);
    }
    @PostMapping("/assignCardToUserStoryAdmin/{idUserStory}/assign/{idCard}")
    public UserStory assignCardToUserStoryAdmin(@PathVariable long idUserStory,@PathVariable long  idCard) {
        return iUserStoryService.AssignCardToUserStoryAdmin(idUserStory,idCard);
    }
    @GetMapping("/getUsersWithUserStoryAndCards/{idUserStory}")
    public ResponseEntity<Map<String, Object>> getUsersWithUserStoryAndCards(@PathVariable long idUserStory){
        return iUserStoryService.getUsersWithUserStoryAndCards(idUserStory);
    }
    @GetMapping("/getUsersEstimationPercentage/{idUserStory}")
    public ResponseEntity<Map<String, Object>> getUsersEstimationPercentage(@PathVariable int idUserStory){
        return iUserStoryService.getUsersEstimationPercentage(idUserStory);
    }

    @GetMapping("/sortDescending")
    public List<UserStory> sortByFinalComplexityDescending() {
        List<UserStory> userStories = userStoryRepo.findAll(); // Récupérer toutes les UserStories depuis la base de données
        iUserStoryService.sortByFinalComplexityDescending(userStories); // Appeler la fonction de tri décroissant
        return userStories; // Retourner la liste triée
    }

    @GetMapping("/sortAscending")
    public List<UserStory> sortByFinalComplexityAscending() {
        List<UserStory> userStories = userStoryRepo.findAll(); // Récupérer toutes les UserStories depuis la base de données
        iUserStoryService.sortByFinalComplexityAscending(userStories); // Appeler la fonction de tri croissant
        return userStories; // Retourner la liste triée
    }
}
