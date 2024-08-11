package com.example.demo.Service;

import com.example.demo.Entity.Card;
import com.example.demo.Entity.UserStory;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface IUserStoryService {
    UserStory addUSAndAssignUsToSprint(UserStory us , long id);
    List<UserStory> getUserStoriesBySprintId(Long id);
    void deleteUserStoryById(Long id);
    UserStory updateUserStory(UserStory us);
    UserStory findById(long id);
//Rihab
List<UserStory> FindAllUserStory();
    UserStory FindUserStoryById (long id);
    UserStory AssignCardToUserStory(long idCard, long idUserStory) ;
    List<Card> getCardsByUserStoryId(Long userStoryId);
    //public UserStory UpdatECardOfUserStory(long idUserStory,long idCard );
    ResponseEntity<List<Map<String, Object>>> getUsersWithAllUserStoriesAndCards(long idSprint)  ;
    ResponseEntity<List<Map<String, Object>>> getUseridWithAllUserStoriesAndCards(long userId);
    List<UserStory> FindUsBySprint (long idSprint);
    ResponseEntity<List<Map<String, Object>>> getUsersWithAllUserStoriesAndNotes(long idSprint);
    Map<String, String> calculateComplexityPercentageForUserStory(int userStoryId);
    //   UserStory updateAssignedCard(long idCard, long idUserStory);
    void DeleteCardFromUserStory(Long userStoryId, Long cardIdLoaded);
    /*  Map<Integer, Map<String, String>> calculateComplexityPercentageForAllUserStories();*/
    UserStory updateCardAssignment(long idUserStory, long idOldCard, long idNewCard) ;
    UserStory AssignCardToUserStoryAdmin(long idCard, long idUserStory) ;
    ResponseEntity<Map<String, Object>> getUsersWithUserStoryAndCards(long idUserStory);
    ResponseEntity<Map<String, Object>> getUsersEstimationPercentage(int idUserStory) ;
    void sortByFinalComplexityDescending(List<UserStory> userStories) ;
    void sortByFinalComplexityAscending(List<UserStory> userStories);}




