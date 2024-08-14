package tn.esprit.pockerplanning.services;

import org.springframework.http.ResponseEntity;
import tn.esprit.pockerplanning.entities.Card;
import tn.esprit.pockerplanning.entities.Sprint;
import tn.esprit.pockerplanning.entities.UserStory;

import java.util.List;
import java.util.Map;

public interface IUserStoryService {
    UserStory addUSAndAssignUsToSprint(UserStory us , String id);
    List<UserStory> getUserStoriesBySprintId(String id);
    void deleteUserStoryById(String id);
    UserStory updateUserStory(UserStory us);
    UserStory findById(String id);
//Rihab
List<UserStory> FindAllUserStory();
    UserStory FindUserStoryById (String id);
    UserStory AssignCardToUserStory(String idCard, String idUserStory) ;
    List<Card> getCardsByUserStoryId(String userStoryId);
    //public UserStory UpdatECardOfUserStory(long idUserStory,long idCard );
    ResponseEntity<List<Map<String, Object>>> getUsersWithAllUserStoriesAndCards(String idSprint)  ;
    ResponseEntity<List<Map<String, Object>>> getUseridWithAllUserStoriesAndCards(String userId);
    List<UserStory> FindUsBySprint (String idSprint);
    ResponseEntity<List<Map<String, Object>>> getUsersWithAllUserStoriesAndNotes(String idSprint);
    Map<String, String> calculateComplexityPercentageForUserStory(String userStoryId);
    //   UserStory updateAssignedCard(long idCard, long idUserStory);
    void DeleteCardFromUserStory(String userStoryId, String cardIdLoaded);
    /*  Map<Integer, Map<String, String>> calculateComplexityPercentageForAllUserStories();*/
    UserStory updateCardAssignment(String idUserStory, String idOldCard, String idNewCard) ;
    UserStory AssignCardToUserStoryAdmin(String idCard, String idUserStory) ;
    ResponseEntity<Map<String, Object>> getUsersWithUserStoryAndCards(String idUserStory);
    ResponseEntity<Map<String, Object>> getUsersEstimationPercentage(String idUserStory) ;
    void sortByFinalComplexityDescending(List<UserStory> userStories) ;
    void sortByFinalComplexityAscending(List<UserStory> userStories);}




