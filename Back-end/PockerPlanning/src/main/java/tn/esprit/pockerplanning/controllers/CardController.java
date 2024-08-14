package tn.esprit.pockerplanning.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pockerplanning.entities.Card;
import tn.esprit.pockerplanning.services.CardService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@RestController

@RequiredArgsConstructor
@RequestMapping("card")
public class CardController {
    private final CardService cardService;


    @GetMapping("/getCardIdByUserStoryAndUser/{userStoryId}/{userId}")//
    public Integer getCardIdByUserStoryAndUser(@PathVariable String userStoryId, @PathVariable long userId){
        return cardService.getCardIdByUserStoryAndUser(userStoryId, String.valueOf(userId));
    }
    @GetMapping("/findCardByIdUser/{idUser}")
    public   ResponseEntity<List<Card>> findCardByIdUser(@PathVariable String idUser){
        return cardService.findCardByIdUser(idUser);
         }
    @GetMapping("/GetAllCard")
   public  List<Card> GetAllCardGetAllCard(){
              return cardService.GetAllCard();
    }
    @GetMapping("/findById/{idCard}")
    public Card findById(long idCard){
              return cardService.findById(idCard);
    }
    @GetMapping("/getComplexityForUserInStory/{idUser}/{idUserStory}")
    public int getComplexityForUserInStory(@PathVariable String idUser, @PathVariable long idUserStory){
        return cardService.getComplexityForUserInStory(idUser, String.valueOf(idUserStory));
    }

}
