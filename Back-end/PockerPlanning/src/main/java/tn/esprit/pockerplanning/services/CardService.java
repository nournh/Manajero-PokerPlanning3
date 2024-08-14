package tn.esprit.pockerplanning.services;

import org.springframework.http.ResponseEntity;
import tn.esprit.pockerplanning.entities.Card;

import java.util.List;

public interface CardService {

    List<Card> GetAllCard();
    Integer getCardIdByUserStoryAndUser(String userStoryId, String userId);
    ResponseEntity<List<Card>> findCardByIdUser(String idUser) ;

    Card findById (long idCard);
    int getComplexityForUserInStory(String idUser, String idUserStory);
}
