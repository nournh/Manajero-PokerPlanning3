package tn.esprit.pockerplanning.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tn.esprit.pockerplanning.entities.Card;
import tn.esprit.pockerplanning.entities.UserStory;
import tn.esprit.pockerplanning.repositories.CardRepository;
import tn.esprit.pockerplanning.repositories.UserRepository;
import tn.esprit.pockerplanning.repositories.UserStoryRepository;

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
    public Integer getCardIdByUserStoryAndUser(String userStoryId, String userId) {
        // Recherche de la User Story par son ID
        UserStory userStory = userStoryRepo.findById(userStoryId).orElse(null);

        if (userStory != null) {
            // Parcourir les cartes associées à la User Story
            for (Card card : userStory.getCardSet()) {
                // Vérifier si la carte est associée à l'user recherché
                if (card.getIdUser().equals(userId)) {
                    // Si c'est le cas, retourner la valeur de la complexité de la carte
                    return card.getComplexity().getValue();
                }
            }
        }

        // Retourner null ou une valeur par défaut si aucune carte n'est trouvée
        return null;
    }

    @Override
    public List<Card> GetAllCard() {
        return cardRepository.findAll();
    }

    @Override
    public ResponseEntity<List<Card>> findCardByIdUser(String idUser) {
        if (idUser == null) {
            // Gérer le cas où idUser est null
            System.out.println("L'ID utilisateur est null.");
            return ResponseEntity.badRequest().build();
        }

        List<Card> cards = cardRepository.findByIdUser(String.valueOf(idUser));
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
        Optional<Card> optionalCard = cardRepository.findById(String.valueOf(idCard));
        return optionalCard.orElse(null); // Retourne la carte si elle est présente, sinon retourne null
    }

    @Override
    public int getComplexityForUserInStory(String idUser, String idUserStory) {
        long userIdLong;
        long userStoryIdLong;
        try {
            userIdLong = Long.parseLong(idUser); // Convert String to long
            userStoryIdLong = Long.parseLong(idUserStory); // Convert String to long
        } catch (NumberFormatException e) {
            return -2; // Return -2 if conversion fails
        }

        // Retrieve the UserStory by its ID
        UserStory userStory = userStoryRepo.findById(String.valueOf(userStoryIdLong)).orElse(null);
        if (userStory != null) {
            // Retrieve all cards associated with the UserStory
            Set<Card> cards = userStory.getCardSet();
            for (Card card : cards) {
                // Check if the card belongs to the given user
                if (card.getIdUser().equals(userIdLong)) {
                    // Return the complexity value if the card belongs to the user
                    return card.getComplexity().getValue();
                }
            }
            // Return -1 if no card is found for the given user
            return -1;
        } else {
            // Return -2 if the UserStory is not found
            return -2;
        }
    }


}
