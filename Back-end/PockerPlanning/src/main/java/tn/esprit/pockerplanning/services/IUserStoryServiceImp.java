package tn.esprit.pockerplanning.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.pockerplanning.entities.*;
import tn.esprit.pockerplanning.entities.enums.Complexity;
import tn.esprit.pockerplanning.repositories.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class IUserStoryServiceImp implements IUserStoryService {
    private final SprintRepository sprintRepository;
    private final UserStoryRepository userStoryRepository;
    private final CardRepository cardRepo;
    private final NoteRepository noteRepository;
    private final UserRepository userRepo;

    @Override
    public UserStory addUSAndAssignUsToSprint(UserStory us, String id) {
        Sprint sprint = sprintRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid sprint Id"));
        if (sprint.getUserStorySet() == null) {
            sprint.setUserStorySet(new HashSet<>());
        }

        sprint.getUserStorySet().add(us);
        us.setSprint(sprint);
        userStoryRepository.save(us);
        return us;
    }

    @Override
    public List<UserStory> getUserStoriesBySprintId(String id) {
        Set<UserStory> userStorySet = userStoryRepository.findUserStoryBySprintId(id);
        return new ArrayList<>(userStorySet);
    }

    @Override
    public void deleteUserStoryById(String id) {
        userStoryRepository.deleteById(id);
    }

    @Override
    public UserStory updateUserStory(UserStory us) {
        return userStoryRepository.save(us);
    }

    @Override
    public UserStory findById(String id) {
        return userStoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user story Id"));
    }

    @Override
    public List<UserStory> FindAllUserStory() {
        return userStoryRepository.findAll();
    }

    @Override
    public UserStory FindUserStoryById(String id) {
        return userStoryRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public UserStory AssignCardToUserStory(String idCard, String idUserStory) {
        Card card = cardRepo.findById(idCard).orElse(null);
        UserStory userStory = userStoryRepository.findById(idUserStory).orElse(null);

        if (card == null || userStory == null) {
            throw new IllegalArgumentException("La carte ou l'histoire utilisateur n'existe pas.");
        }

        boolean userAlreadyAssigned = userStory.getCardSet().stream()
                .anyMatch(existingCard -> existingCard.getIdUser() != null
                        && existingCard.getIdUser().equals(card.getIdUser()));

        if (userAlreadyAssigned) {
            throw new IllegalArgumentException("L'utilisateur ne peut pas affecter deux cartes à la même histoire utilisateur.");
        }

        userStory.getCardSet().add(card);

        Map<String, String> updatedComplexityPercentage = calculateComplexityPercentageForUserStory(idUserStory);

        return userStoryRepository.save(userStory);
    }

    @Transactional
    @Override
    public List<Card> getCardsByUserStoryId(String userStoryId) {
        return userStoryRepository.findCardsByUserStoryId(userStoryId);
    }

    @Override
    public ResponseEntity<List<Map<String, Object>>> getUsersWithAllUserStoriesAndCards(String idSprint) {
        try {
            List<Map<String, Object>> response = new ArrayList<>();
            List<User> users = userRepo.findDevelopersWithCards();

            for (User user : users) {
                for (Project project : user.getProjectSet()) {
                    for (Sprint sprint : project.getSprintSet()) {
                        if (sprint.getId().equals(idSprint)) {
                            for (UserStory userStory : sprint.getUserStorySet()) {
                                boolean storyAdded = response.stream().anyMatch(storyMap -> storyMap.get("id").equals(userStory.getId()));
                                if (!storyAdded) {
                                    Map<String, Object> userStoryDetails = new HashMap<>();
                                    userStoryDetails.put("id", userStory.getId());
                                    userStoryDetails.put("description", userStory.getDescription());
                                    List<Map<String, Object>> cardsList = new ArrayList<>();
                                    for (Card card : userStory.getCardSet()) {
                                        Map<String, Object> cardDetails = new HashMap<>();
                                        cardDetails.put("id", card.getId());
                                        cardDetails.put("complexity", card.getComplexity().getValue());
                                        User cardUser = userRepo.findById(card.getIdUser()).orElse(null);
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

            if (response.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error retrieving users with user stories and cards: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonList(Collections.singletonMap("error", "Error retrieving users with user stories and cards. Please try again later.")));
        }
    }

    @Override
    public ResponseEntity<List<Map<String, Object>>> getUseridWithAllUserStoriesAndCards(String userId) {
        try {
            List<Map<String, Object>> response = new ArrayList<>();
            User user = userRepo.findById(userId).orElse(null);
            if (user == null) {
                return ResponseEntity.notFound().build();
            }

            for (UserStory userStory : userStoryRepository.findAll()) {
                if (isUserAssignedToUserStory(userStory, userId)) {
                    Map<String, Object> userStoryDetails = new HashMap<>();
                    userStoryDetails.put("IdUserStory", userStory.getId());
                    userStoryDetails.put("description", userStory.getDescription());
                    List<Map<String, Object>> cardsList = new ArrayList<>();
                    for (Card card : userStory.getCardSet()) {
                        if (card.getIdUser().equals(userId)) {
                            Map<String, Object> cardDetails = new HashMap<>();
                            cardDetails.put("IdCard", card.getId());
                            cardDetails.put("Estimation", card.getComplexity().getValue());
                            cardDetails.put("UserFirstname", user.getFirstname());
                            cardsList.add(cardDetails);
                        }
                    }
                    userStoryDetails.put("cards", cardsList);
                    response.add(userStoryDetails);
                }
            }

            if (response.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error retrieving user stories and cards for user with id {}: {}", userId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonList(Collections.singletonMap("error", "Error retrieving user stories and cards. Please try again later.")));
        }
    }

    @Override
    public List<UserStory> FindUsBySprint(String idSprint) {
        return userStoryRepository.findBySprintId(idSprint);
    }

    private boolean isUserAssignedToUserStory(UserStory userStory, String userId) {
        return userStory.getCardSet().stream().anyMatch(card -> userId.equals(card.getIdUser()));
    }

    @Override
    public ResponseEntity<List<Map<String, Object>>> getUsersWithAllUserStoriesAndNotes(String idSprint) {
        try {
            List<Map<String, Object>> response = new ArrayList<>();
            List<User> users = userRepo.findDevelopersWithNotes();

            for (User user : users) {
                for (Project project : user.getProjectSet()) {
                    for (Sprint sprint : project.getSprintSet()) {
                        if (sprint.getId().equals(idSprint)) {
                            for (UserStory userStory : sprint.getUserStorySet()) {
                                boolean storyAdded = response.stream().anyMatch(storyMap -> storyMap.get("id").equals(userStory.getId()));
                                if (!storyAdded) {
                                    Map<String, Object> userStoryDetails = new HashMap<>();
                                    userStoryDetails.put("id", userStory.getId());
                                    userStoryDetails.put("description", userStory.getDescription());
                                    List<Map<String, Object>> notesList = new ArrayList<>();
                                    for (Note note : userStory.getNoteSet()) {
                                        Map<String, Object> noteDetails = new HashMap<>();
                                        noteDetails.put("id", note.getId());
                                        noteDetails.put("description", note.getDescription());
                                        User notesUser = userRepo.findById(note.getIdUser()).orElse(null);
                                        noteDetails.put("FirstName", notesUser != null ? notesUser.getFirstname() : "Unassigned");
                                        notesList.add(noteDetails);
                                    }
                                    userStoryDetails.put("notes", notesList);
                                    response.add(userStoryDetails);
                                }
                            }
                        }
                    }
                }
            }

            if (response.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error retrieving users with user stories and notes: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonList(Collections.singletonMap("error", "Error retrieving users with user stories and notes. Please try again later.")));
        }
    }

    @Override
    public Map<String, String> calculateComplexityPercentageForUserStory(String userStoryId) {
        UserStory userStory = userStoryRepository.findById(userStoryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid User Story Id"));

        Map<Complexity, Long> complexityCounts = userStory.getCardSet().stream()
                .filter(card -> card.getComplexity() != null)
                .collect(Collectors.groupingBy(Card::getComplexity, Collectors.counting()));

        long totalCards = userStory.getCardSet().size();

        return complexityCounts.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().name(),
                        entry -> String.format("%.2f", (entry.getValue() * 100.0 / totalCards))
                ));
    }

    @Override
    public void DeleteCardFromUserStory(String userStoryId, String cardIdLoaded) {

    }

    @Override
    public UserStory updateCardAssignment(String idUserStory, String idOldCard, String idNewCard) {
        return null;
    }

    @Override
    public UserStory AssignCardToUserStoryAdmin(String idCard, String idUserStory) {
        return null;
    }

    @Override
    public ResponseEntity<Map<String, Object>> getUsersWithUserStoryAndCards(String idUserStory) {
        return null;
    }

    @Override
    public ResponseEntity<Map<String, Object>> getUsersEstimationPercentage(String idUserStory) {
        return null;
    }

    @Override
    public void sortByFinalComplexityDescending(List<UserStory> userStories) {

    }

    @Override
    public void sortByFinalComplexityAscending(List<UserStory> userStories) {

    }
}
