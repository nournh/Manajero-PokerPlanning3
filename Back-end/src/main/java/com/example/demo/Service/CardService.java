package com.example.demo.Service;

import com.example.demo.Entity.Card;
import org.springframework.http.ResponseEntity;


import java.util.List;

public interface CardService {

    List<Card> GetAllCard();
    Integer getCardIdByUserStoryAndUser(Long userStoryId, Long userId);
    ResponseEntity<List<Card>> findCardByIdUser(Long idUser) ;

    Card findById (long idCard);
    int getComplexityForUserInStory(long idUser, long idUserStory);
}
