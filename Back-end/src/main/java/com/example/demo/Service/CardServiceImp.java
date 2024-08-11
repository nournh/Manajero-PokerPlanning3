package com.example.demo.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import  com.example.demo.Entity.Card;
import com.example.demo.Entity.UserStory;
import com.example.demo.Repositories.CardRepository;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.Repositories.UserStoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CardServiceImp implements CardService {
    public final CardRepository cardRepository;
    private final UserStoryRepository userStoryRepo;
    private final UserRepository userRepo;
    @Override
    public Integer getCardIdByUserStoryAndUser(Long userStoryId, Long userId) {
        // Recherche de la User Story par son ID
        UserStory userStory = userStoryRepo.findById(userStoryId).orElse(null);

        if (userStory != null) {
            // Parcourir les cartes associées à la User Story
            for (Card card : userStory.getCardSet()) {
                // Vérifier si la carte est associée à l'user recherché
                if (card.getIdUser()==(userId)) {
                    // Si c'est le cas, retourner l'ID de la carte
                    return   card.getComplexity().getValue();                }
            }
        }

        // Si aucune carte n'est trouvée, retourner null
        return null;
    }
    @Override
    public List<Card> GetAllCard() {
        return cardRepository.findAll();
    }

    @Override
    public ResponseEntity<List<Card>> findCardByIdUser(Long idUser) {
        if (idUser == null) {
            // Gérer le cas où idUser est null
            System.out.println("L'ID utilisateur est null.");
            return ResponseEntity.badRequest().build();
        }

        List<Card> cards = cardRepository.findByIdUser(idUser);
        if (cards != null) {
            for (Card card : cards) {
                if (card != null && card.getComplexity() != null) {
                    int complexityValue = card.getComplexity().getValue(); // Obtenir la valeur d'énumération sous forme de chiffre
                    System.out.println("Complexity value for card with ID " + card.getId() + ": " + complexityValue);
                } else {
                    System.out.println("Card or its complexity is null.");
                }
            }
            return ResponseEntity.ok(cards);
        } else {
            System.out.println("No cards found for the given user ID.");
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public Card findById(long idCard) {
        Optional<Card> optionalCard = cardRepository.findById(idCard);
        return optionalCard.orElse(null); // Retourne la carte si elle est présente, sinon retourne null
    }

    @Override
    public int getComplexityForUserInStory(long idUser, long idUserStory) {
        // Récupérer l'UserStory par son ID
        UserStory userStory = userStoryRepo.findById(idUserStory).orElse(null);
        if (userStory != null) {
            // Récupérer toutes les cartes associées à l'UserStory
            Set<Card> cards = userStory.getCardSet();
            for (Card card : cards) {
                // Vérifier si la carte appartient à l'utilisateur donné
                if (card.getIdUser() == idUser) {
                    // Si la carte appartient à l'utilisateur donné, retourner la valeur de complexité
                    return card.getComplexity().getValue();
                }
            }
            // Si aucune carte n'est trouvée pour l'utilisateur donné, retourner -1
            return -1;
        } else {
            // Si l'UserStory n'est pas trouvée, retourner -2
            return -2;
        }
    }




    }
