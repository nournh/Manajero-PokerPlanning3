package com.example.demo.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Entity.Card;
import com.example.demo.Service.CardService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@RestController

@RequiredArgsConstructor
@RequestMapping("card")
public class CardController {
    private final CardService cardService;


    @GetMapping("/getCardIdByUserStoryAndUser/{userStoryId}/{userId}")//
    public Integer getCardIdByUserStoryAndUser(@PathVariable long userStoryId,@PathVariable long userId){
        return cardService.getCardIdByUserStoryAndUser(userStoryId,userId);
    }
    @GetMapping("/findCardByIdUser/{idUser}")
    public   ResponseEntity<List<Card>> findCardByIdUser(@PathVariable long idUser){
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
    public int getComplexityForUserInStory(@PathVariable long idUser,@PathVariable long idUserStory){
        return cardService.getComplexityForUserInStory(idUser,idUserStory);
    }

}
