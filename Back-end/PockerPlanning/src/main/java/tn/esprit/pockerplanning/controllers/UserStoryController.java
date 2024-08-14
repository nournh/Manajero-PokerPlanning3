package tn.esprit.pockerplanning.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pockerplanning.entities.Card;
import tn.esprit.pockerplanning.entities.UserStory;
import tn.esprit.pockerplanning.repositories.UserStoryRepository;
import tn.esprit.pockerplanning.services.IUserStoryService;

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
    public UserStory addUSAndAssignUsToSprint(@RequestBody UserStory us,@PathVariable String id) {
        return iUserStoryService.addUSAndAssignUsToSprint(us,id);
    }
    @GetMapping("/getUSBySprintId/{id}")
    public List<UserStory> getUserStoriesBySprintId(@PathVariable String id) {
        return iUserStoryService.getUserStoriesBySprintId(id);
    }
    @DeleteMapping("/deleteUS/{id}")
    public void deleteUserStoryById(@PathVariable String id) {
        iUserStoryService.deleteUserStoryById(id);
    }
    @PutMapping("/updateUS")
    public UserStory updateUserStory(@RequestBody UserStory us) {
        return iUserStoryService.updateUserStory(us);
    }
    @GetMapping("/getUSbyId/{id}")
    public UserStory findById(@PathVariable String id) {
        return iUserStoryService.findById(id);
    }
    //Rihab
    @GetMapping("/ListUserStory")// pour developpeur par sprint
    public List<UserStory> getAllUserStory(){
        return iUserStoryService.FindAllUserStory();
    }
    @GetMapping("/{id}")// pour developpeur pourestimer ou noter
    public UserStory findUserStoryById(@PathVariable String id){
        return iUserStoryService.FindUserStoryById(String.valueOf(id));
    }
    @PostMapping("/AssignCardToUserStory/{idCard}/{idUserStory}")//affectation card a us de la part de devlop
    public UserStory AssignCardToUserStory(@PathVariable String idCard, @PathVariable String idUserStory)
    {
        return iUserStoryService.AssignCardToUserStory(idCard, String.valueOf(idUserStory));
    }

    @GetMapping ("/getCardsByUserStoryId/{userStoryId}")//dhaherli mch bech nest3mlha
    public  List<Card> getCardsByUserStoryId(@PathVariable String userStoryId){
        return iUserStoryService.getCardsByUserStoryId(String.valueOf(userStoryId));
    }

    @GetMapping ("/getUsersWithAllUserStoriesAndCards/{idSprint}")//chef de projet yra  us avec estimation et nom de user
    public ResponseEntity<List<Map<String, Object>>> getUsersWithAllUserStoriesAndCards(@PathVariable String idSprint){
        return iUserStoryService.getUsersWithAllUserStoriesAndCards(idSprint);
    }
    @GetMapping ("/getUserIdWithAllUserStoriesAndCards/{userId}")//afficher lkol developpeur
    // userStory et les cards affecter avec leur estimation
    public ResponseEntity<List<Map<String, Object>>> getUserWithAllUserStoriesAndCard(@PathVariable  String userId){
        return iUserStoryService.getUsersWithAllUserStoriesAndCards(userId);
    }
    @GetMapping("/FindUsBySprint/{idSprint}")
    public List<UserStory> FindUsBySprint(@PathVariable  String idSprint) {
        return iUserStoryService.FindUsBySprint(String.valueOf(idSprint));
    }
    @GetMapping("/getUsersWithAllUserStoriesAndnotes/{idSprint}")
    public ResponseEntity<List<Map<String, Object>>> getUsersWithAllUserStoriesAndnotes(@PathVariable long idSprint){
        return iUserStoryService.getUsersWithAllUserStoriesAndNotes(String.valueOf(idSprint));
    }
    /*@PutMapping("/updateAssignedCard/{idCard}/{idUserStory}")
    public void updateAssignment(long userStoryId, long oldCardSetId, long newCardSetId){
       return USService.updateAssignment(userStoryId,oldCardSetId,newCardSetId);
    }*/
    @DeleteMapping("/DeleteCardFromUserStory/{userStoryId}/{cardIdLoaded}")
    public void DeleteCardFromUserStory(@PathVariable String userStoryId, @PathVariable long cardIdLoaded) {
        iUserStoryService.DeleteCardFromUserStory(userStoryId, String.valueOf(cardIdLoaded));    }
    @PutMapping("/updateCardAssignment/{idUserStory}/{idOldCard}/{idNewCard}")
    public    UserStory updateCardAssignment(@PathVariable String idUserStory, @PathVariable String idOldCard, @PathVariable long idNewCard) {
        return  iUserStoryService.updateCardAssignment(idUserStory, idOldCard, String.valueOf(idNewCard));
    }

    @GetMapping("/calculateComplexityPercentageForUserStory/{userStoryId}")
    public Map<String, String> calculateComplexityPercentageForUserStory(@PathVariable int userStoryId){
        return iUserStoryService.calculateComplexityPercentageForUserStory(String.valueOf(userStoryId));
    }
    @PostMapping("/assignCardToUserStoryAdmin/{idUserStory}/assign/{idCard}")
    public UserStory assignCardToUserStoryAdmin(@PathVariable String idUserStory, @PathVariable long  idCard) {
        return iUserStoryService.AssignCardToUserStoryAdmin(idUserStory, String.valueOf(idCard));
    }
    @GetMapping("/getUsersWithUserStoryAndCards/{idUserStory}")
    public ResponseEntity<Map<String, Object>> getUsersWithUserStoryAndCards(@PathVariable long idUserStory){
        return iUserStoryService.getUsersWithUserStoryAndCards(String.valueOf(idUserStory));
    }
    @GetMapping("/getUsersEstimationPercentage/{idUserStory}")
    public ResponseEntity<Map<String, Object>> getUsersEstimationPercentage(@PathVariable String idUserStory){
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
