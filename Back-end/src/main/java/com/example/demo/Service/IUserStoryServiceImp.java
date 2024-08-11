package com.example.demo.Service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.Entity.*;
import com.example.demo.Entity.enums.Complexity;
import com.example.demo.Repositories.*;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class IUserStoryServiceImp implements IUserStoryService{
      private final SprintRepository sprintRepository;
      private final UserStoryRepository userStoryRepository;
      private final CardRepository cardRepo;
      private final NoteRepository noteRepository;
      private final UserRepository userRepo;

    @Override
    public UserStory addUSAndAssignUsToSprint(UserStory us, long id) {
        Sprint sprint = sprintRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid sprint Id"));
        // Vérifier si le Set de userStories du sprint est null, puis l'initialiser si nécessaire
        if (sprint.getUserStorySet() == null) {
            sprint.setUserStorySet(new HashSet<>());
        }

        //Ajouter le user story au set des userstories du sprint
        sprint.getUserStorySet().add(us);
        //Ajouter le sprint au user story
        us.setSprint(sprint);
        //Sauvegarder les changements
        userStoryRepository.save(us);
        return us;


    }

    @Override
    public List<UserStory> getUserStoriesBySprintId(Long id) {
        Set<UserStory> userStorySet = userStoryRepository.findUserStoryBySprintId(id);
        List<UserStory> userStoryList = new ArrayList<>(userStorySet);
        return userStoryList;
    }

    @Override
    public void deleteUserStoryById(Long id) {
        userStoryRepository.deleteById(id);
    }

    @Override
    public UserStory updateUserStory(UserStory us) {
        return userStoryRepository.save(us);
    }

    @Override
    public UserStory findById(long id) {
        return userStoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user story Id"));

    }
    //Rihab


    @Override
    public  List<UserStory> FindAllUserStory() {
        return (List<UserStory>) userStoryRepository.findAll();
    }

    @Override
    public UserStory FindUserStoryById(long id) {
        return userStoryRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public UserStory AssignCardToUserStory(long idCard, long idUserStory) {
        Card card = cardRepo.findById(idCard).orElse(null);
        UserStory userStory = userStoryRepository.findById(idUserStory).orElse(null);

        if (card == null || userStory == null) {
            throw new IllegalArgumentException("La carte ou l'histoire utilisateur n'existe pas.");
        }

        // Vérifier si l'utilisateur est déjà associé à une carte dans cette histoire utilisateur
        boolean userAlreadyAssigned = userStory.getCardSet().stream()
                .anyMatch(existingCard -> existingCard.getIdUser() != 0 && existingCard.getIdUser() == card.getIdUser());

        if (userAlreadyAssigned) {
            throw new IllegalArgumentException("L'utilisateur ne peut pas affecter deux cartes à la même histoire utilisateur.");
        }

        // Ajouter la carte à l'histoire utilisateur
        userStory.getCardSet().add(card);

        // Mettre à jour les pourcentages de complexité
        Map<String, String> updatedComplexityPercentage = calculateComplexityPercentageForUserStory((int) idUserStory);


        // Enregistrez la mise à jour de l'histoire utilisateur
        UserStory updatedUserStory = userStoryRepository.save(userStory);

        return updatedUserStory;
    }
    @Transactional
    @Override
    public List<Card> getCardsByUserStoryId(Long userStoryId) {
        return userStoryRepository.findCardsByUserStoryId(userStoryId);
    }




    @Override
    public ResponseEntity<List<Map<String, Object>>> getUsersWithAllUserStoriesAndCards(long idSprint) {
        try {
            List<Map<String, Object>> response = new ArrayList<>();

            // Récupérer tous les utilisateurs avec le rôle "DEVELOPER" et qui ont des cartes associées
            List<User> users = userRepo.findDevelopersWithCards();

            // Pour chaque utilisateur, récupérer les histoires utilisateur avec les cartes associées du sprint spécifié
            for (User user : users) {
                for (Project project : user.getProjectSet()) {
                    for (Sprint sprint : project.getSprintSet()) {
                        if (sprint.getId() == idSprint) { // Vérifier si c'est le sprint recherché
                            for (UserStory userStory : sprint.getUserStorySet()) {
                                // Vérifier si cette histoire utilisateur a déjà été ajoutée à la réponse
                                boolean storyAdded = false;
                                for (Map<String, Object> storyMap : response) {
                                    if (storyMap.get("id").equals(userStory.getId())) {
                                        storyAdded = true;
                                        break;
                                    }
                                }
                                if (!storyAdded) {
                                    Map<String, Object> userStoryDetails = new HashMap<>();
                                    userStoryDetails.put("id", userStory.getId());
                                    userStoryDetails.put("description", userStory.getDescription());
                                    List<Map<String, Object>> cardsList = new ArrayList<>();
                                    for (Card card : userStory.getCardSet()) {
                                        Map<String, Object> cardDetails = new HashMap<>();
                                        cardDetails.put("id", card.getId());
                                        cardDetails.put("complexity", card.getComplexity().getValue());
                                        Long cardUserId = card.getIdUser();
                                        User cardUser = null;
                                        if (cardUserId != null) {
                                            cardUser = userRepo.findById(cardUserId).orElse(null);
                                        }
                                        cardDetails.put("FirstName", cardUser != null ? cardUser.getFirstname() : "Unassigned");
                                        cardDetails.put("LastName", cardUser != null ? cardUser.getLastname() : "Unassigned");
                                        cardsList.add(cardDetails);
                                    }
                                    userStoryDetails.put("cards", cardsList);
                                    response.add(userStoryDetails);
                                }
                            }
                        }
                    }
                }
            }

            // Vérifier si la liste est vide
            if (response.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            // Renvoyer la liste des histoires utilisateur avec les détails de la carte associée
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Loguer l'exception
            System.err.println("Error retrieving users with user stories and cards: " + e.getMessage());
            // Renvoyer une réponse d'erreur
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonList(Collections.singletonMap("error", "Error retrieving users with user stories and cards. Please try again later.")));
        }
    }

    @Override
    public ResponseEntity<List<Map<String, Object>>> getUseridWithAllUserStoriesAndCards(long userId) {
        try {
            List<Map<String, Object>> response = new ArrayList<>();

            // Récupérer l'utilisateur spécifié
            User user = userRepo.findById(userId).orElse(null);
            if (user == null) {
                return ResponseEntity.notFound().build(); // Utilisateur non trouvé
            }

            // Parcourir toutes les histoires utilisateur pour trouver celles qui sont affectées à l'utilisateur spécifié
            for (UserStory userStory : userStoryRepository.findAll()) {
                // Vérifier si l'utilisateur spécifié est affecté à cette histoire utilisateur
                if (isUserAssignedToUserStory(userStory, userId)) {
                    // Ajouter les détails de l'histoire utilisateur à la réponse
                    Map<String, Object> userStoryDetails = new HashMap<>();
                    userStoryDetails.put("IdUserStory", userStory.getId());
                    userStoryDetails.put("description", userStory.getDescription());

                    // Ajouter les cartes associées à cette histoire utilisateur à la réponse
                    List<Map<String, Object>> cardsList = new ArrayList<>();
                    for (Card card : userStory.getCardSet()) {
                        if (card.getIdUser() == userId) {
                            Map<String, Object> cardDetails = new HashMap<>();
                            cardDetails.put("IdCard", card.getId());
                            cardDetails.put("Estimation", card.getComplexity().getValue());
                            cardDetails.put("UserFirstname", user.getFirstname()); // Nom de l'utilisateur de la carte
                            cardsList.add(cardDetails);
                        }
                    }
                    userStoryDetails.put("cards", cardsList);

                    // Ajouter l'histoire utilisateur avec les cartes associées à la réponse
                    response.add(userStoryDetails);
                }
            }

            // Vérifier si la liste est vide
            if (response.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            // Renvoyer la liste des histoires utilisateur avec les détails de la carte associée
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Loguer l'exception
            System.err.println("Error retrieving user stories and cards for user with id " + userId + ": " + e.getMessage());
            // Renvoyer une réponse d'erreur
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonList(Collections.singletonMap("error", "Error retrieving user stories and cards. Please try again later.")));
        }
    }

    @Override
    public List<UserStory> FindUsBySprint(long idSprint) {
        List<UserStory> userStories = userStoryRepository.findBySprintId(idSprint);

        for (UserStory userStory : userStories) {
            // Récupérer la valeur de finalComplexity sous forme d'entier
            int finalComplexityValue = userStory.getFinalcomplexity().getValue();
            // Utiliser finalComplexityValue comme nécessaire
        }

        return userStories;
    }



    // Méthode utilitaire pour vérifier si un utilisateur est affecté à une histoire utilisateur donnée
    private boolean isUserAssignedToUserStory(UserStory userStory, Long userId) {
        for (Card card : userStory.getCardSet()) {
            Long cardUserId = card.getIdUser();
            if (cardUserId != null && cardUserId.equals(userId)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public ResponseEntity<List<Map<String, Object>>> getUsersWithAllUserStoriesAndNotes(long idSprint) {
        try {
            List<Map<String, Object>> response = new ArrayList<>();

            // Récupérer tous les utilisateurs avec le rôle "DEVELOPER" et qui ont des cartes associées
            List<User> users = userRepo.findDevelopersWithNotes();

            // Pour chaque utilisateur, récupérer les histoires utilisateur avec les cartes associées du sprint spécifié
            for (User user : users) {
                for (Project project : user.getProjectSet()) {
                    for (Sprint sprint : project.getSprintSet()) {
                        if (sprint.getId() == idSprint) { // Vérifier si c'est le sprint recherché
                            for (UserStory userStory : sprint.getUserStorySet()) {
                                // Vérifier si cette histoire utilisateur a déjà été ajoutée à la réponse
                                boolean storyAdded = false;
                                for (Map<String, Object> storyMap : response) {
                                    if (storyMap.get("id").equals(userStory.getId())) {
                                        storyAdded = true;
                                        break;
                                    }
                                }
                                if (!storyAdded) {
                                    Map<String, Object> userStoryDetails = new HashMap<>();
                                    userStoryDetails.put("id", userStory.getId());
                                    userStoryDetails.put("description", userStory.getDescription());
                                    List<Map<String, Object>> cardsList = new ArrayList<>();
                                    for (Note note : userStory.getNoteSet()) {
                                        Map<String, Object> noteDetails = new HashMap<>();
                                        noteDetails.put("id", note.getId());
                                        noteDetails.put("description", note.getDescription());
                                        Long noteUserId = note.getIdUser();
                                        User notesUser = null;
                                        if (noteUserId != null) {
                                            notesUser = userRepo.findById(noteUserId).orElse(null);
                                        }
                                        noteDetails.put("FirstName", notesUser != null ? notesUser.getFirstname() : "Unassigned");
                                        cardsList.add(noteDetails);
                                    }
                                    userStoryDetails.put("notes", cardsList);
                                    response.add(userStoryDetails);
                                }
                            }
                        }
                    }
                }
            }

            // Vérifier si la liste est vide
            if (response.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            // Renvoyer la liste des histoires utilisateur avec les détails de la carte associée
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Loguer l'exception
            System.err.println("Error retrieving users with user stories and notes: " + e.getMessage());
            // Renvoyer une réponse d'erreur
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonList(Collections.singletonMap("error", "Error retrieving users with user stories and cards. Please try again later.")));
        }
    }

    @Transactional
    @Override
    public void DeleteCardFromUserStory(Long userStoryId, Long cardIdLoaded) {
        UserStory userStory = userStoryRepository.findById(userStoryId).orElseThrow(() ->
                new EntityNotFoundException("UserStory not found with id: " + userStoryId));

        // Remove the card from the user story's list of cards
        userStory.getCardSet().removeIf(card -> card.getId()==(cardIdLoaded));

        // Save the changes
        userStoryRepository.save(userStory);
    }


    @Override
    public Map<String, String> calculateComplexityPercentageForUserStory(int userStoryId) {
        Map<String, String> userStoryComplexityPercentage = new HashMap<>();

        // Query to get the count of total cards for the given user story
        String countCardsQuery = "SELECT COUNT(*) FROM user_story_card_set WHERE user_story_id = ?";
        int totalCards = jdbcTemplate.queryForObject(countCardsQuery, Integer.class, userStoryId);

        // If there are no cards, mark the UserStory as "en cours"
        if (totalCards == 0) {
            userStoryComplexityPercentage.put("Statut", "En cours");
        } else {
            // Query to get the count of cards for each complexity for the given user story
            String complexityQuery = "SELECT c.complexity, COUNT(*) " +
                    "FROM user_story_card_set u " +
                    "JOIN card c ON u.card_set_id = c.id " +
                    "WHERE u.user_story_id = ? " +
                    "GROUP BY c.complexity";

            List<Map<String, Object>> complexityCounts = jdbcTemplate.queryForList(complexityQuery, userStoryId);

            double maxPercentage = 0.0; // Variable to store the maximum percentage
            String finalComplexity = null; // Variable to store the final complexity

            // Calculate percentage for each complexity
            for (Map<String, Object> row : complexityCounts) {
                String complexity = (String) row.get("complexity");
                int count = ((Number) row.get("COUNT(*)")).intValue();
                double percentage = ((double) count / totalCards) * 100;
                String formattedPercentage = String.format("%.0f%%", percentage); // Format percentage with no decimal places
                userStoryComplexityPercentage.put(complexity, formattedPercentage);

                // Check if the current percentage is greater than the maximum percentage found so far
                if (percentage >= maxPercentage) {
                    maxPercentage = percentage;
                    finalComplexity = complexity;
                }
            }

            // Check for equality of maximum percentages
            boolean equalPercentages = true;
            for (Map<String, Object> row : complexityCounts) {
                int count = ((Number) row.get("COUNT(*)")).intValue();
                double percentage = ((double) count / totalCards) * 100;
                if (percentage != maxPercentage) {
                    equalPercentages = false;
                    break;
                }
            }

            // If all percentages are equal, mark final complexity as "En cours"

            // Assign final complexity to the user story
            assignFinalComplexityToUserStory(userStoryId, finalComplexity);
        }

        return userStoryComplexityPercentage;
    }

    private void assignFinalComplexityToUserStory(int userStoryId, String complexity) {
        // Update the user story with the final complexity
        String updateQuery = "UPDATE user_story SET finalcomplexity = ? WHERE id = ?";
        jdbcTemplate.update(updateQuery, complexity, userStoryId);
    }


    @Override
    @Transactional
    public UserStory updateCardAssignment(long idUserStory, long idOldCard, long idNewCard) {

        UserStory userStory = userStoryRepository.findById(idUserStory).orElse(null);

        if (userStory == null) {
            throw new IllegalArgumentException("L'histoire utilisateur avec l'ID spécifié n'existe pas.");
        }

        //  Supprimer l'association existante entre cette userStory et la carte avec l'ID 6
        boolean removed = userStory.getCardSet().removeIf(card -> card.getId() == idOldCard);

        if (!removed) {
            throw new IllegalArgumentException("L'association entre l'histoire utilisateur et la carte spécifiée n'existe pas.");
        }

        //  Ajouter la nouvelle association entre cette histoire utilisateur et la carte avec l'ID 7
        Card newCard = cardRepo.findById(idNewCard).orElse(null);

        if (newCard == null) {
            throw new IllegalArgumentException("La nouvelle carte spécifiée n'existe pas.");
        }

        userStory.getCardSet().add(newCard);

        // Enregistrer les modifications dans la base de données
        return userStoryRepository.save(userStory);
    }


    @Override
    @Transactional
    public  UserStory AssignCardToUserStoryAdmin(long idUserStory,long idCard) {
        // Récupérer la carte et l'histoire utilisateur depuis la base de données
        Card card = cardRepo.findById(idCard).orElseThrow(() -> new IllegalArgumentException("La carte n'existe pas."));
        UserStory userStory = userStoryRepository.findById(idUserStory).orElseThrow(() -> new IllegalArgumentException("L'histoire utilisateur n'existe pas."));

        // Définir la finalComplexity de l'histoire utilisateur avec la complexité de la carte
        userStory.setFinalcomplexity(card.getComplexity());

        // Enregistrer la mise à jour de l'histoire utilisateur
        UserStory updatedUserStory = userStoryRepository.save(userStory);

        return updatedUserStory;
    }

    @Override
    public ResponseEntity<Map<String, Object>> getUsersWithUserStoryAndCards(long idUserStory) {
        try {
            Map<String, Object> response = new HashMap<>();

            // Récupérer l'objet UserStory correspondant à l'id spécifié
            Optional<UserStory> userStoryOptional = userStoryRepository.findById(idUserStory);
            if (!userStoryOptional.isPresent()) {
                // Si aucune histoire utilisateur correspondante n'est trouvée, renvoyer une réponse 404
                return ResponseEntity.notFound().build();
            }

            UserStory userStory = userStoryOptional.get();

            // Récupérer tous les utilisateurs avec le rôle "DEVELOPER" et qui ont des cartes associées
            List<User> users = userRepo.findDevelopersWithCards();

            // Récupérer les détails de l'histoire utilisateur spécifiée
            response.put("id", userStory.getId());
            response.put("description", userStory.getDescription());
            List<Map<String, Object>> cardsList = new ArrayList<>();
            for (Card card : userStory.getCardSet()) {
                Map<String, Object> cardDetails = new HashMap<>();
                cardDetails.put("id", card.getId());
                cardDetails.put("complexity", card.getComplexity().getValue());
                Long cardUserId = card.getIdUser();
                User cardUser = null;
                if (cardUserId != null) {
                    cardUser = userRepo.findById(cardUserId).orElse(null);
                }
                cardDetails.put("FirstName", cardUser != null ? cardUser.getFirstname() : "Unassigned");
                cardsList.add(cardDetails);
            }
            response.put("cards", cardsList);

            // Renvoyer les détails de l'histoire utilisateur avec les détails de la carte associée
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Loguer l'exception
            System.err.println("Error retrieving user story with cards: " + e.getMessage());
            // Renvoyer une réponse d'erreur
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Error retrieving user story with cards. Please try again later."));
        }
    }




    @Override
    public ResponseEntity<Map<String, Object>> getUsersEstimationPercentage(int idUserStory) {
        try {
            Map<String, Object> response = new HashMap<>();

            // Récupérer les pourcentages de complexité pour l'histoire utilisateur spécifiée
            Map<String, String> complexityPercentages = calculateComplexityPercentageForUserStory(idUserStory);

            // Récupérer les détails des utilisateurs et des cartes associées à l'histoire utilisateur spécifiée
            ResponseEntity<Map<String, Object>> userStoryResponse = getUsersWithUserStoryAndCards(idUserStory);
            if (userStoryResponse.getStatusCode() != HttpStatus.OK) {

                return userStoryResponse;
            }

            // Ajouter les pourcentages de complexité aux détails de l'histoire utilisateur
            Map<String, Object> userStoryDetails = userStoryResponse.getBody();
            List<Map<String, Object>> cardsList = (List<Map<String, Object>>) userStoryDetails.get("cards");
            for (Map<String, Object> cardDetails : cardsList) {
                Integer complexity = ((Number) cardDetails.get("complexity")).intValue();
                String percentage = complexityPercentages.get(complexity.toString());
                cardDetails.put("complexityPercentage", percentage);
            }
            response.put("cards", cardsList);

            // Ajouter les autres détails de l'histoire utilisateur
            response.put("id", userStoryDetails.get("id"));
            response.put("description", userStoryDetails.get("description"));

            // Renvoyer les détails de l'histoire utilisateur avec les pourcentages de complexité et les détails de la carte associée
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Loguer l'exception
            System.err.println("Error retrieving user story with complexity percentages and cards: " + e.getMessage());
            // Renvoyer une réponse d'erreur
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Error retrieving user story with complexity percentages and cards. Please try again later."));
        }
    }
    // Fonction pour trier les user stories par complexité finale en ordre décroissant


    // Fonction pour trier les user stories par complexité finale en ordre décroissant
    public void sortByFinalComplexityDescending(List<UserStory> userStories) {
        Collections.sort(userStories, Comparator.comparing((UserStory userStory) -> userStory.getFinalcomplexity()).reversed());
    }

    // Fonction pour trier les user stories par complexité finale en ordre croissant
    public void sortByFinalComplexityAscending(List<UserStory> userStories) {
        Collections.sort(userStories, Comparator.comparing((UserStory userStory) -> userStory.getFinalcomplexity()));
    }
}
