package tn.esprit.pockerplanning.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.pockerplanning.entities.Card;
import tn.esprit.pockerplanning.entities.UserStory;

import java.util.List;
import java.util.Set;

public interface UserStoryRepository extends MongoRepository<UserStory, String> {
    Set<UserStory> findUserStoryBySprintId(String id);

    // Custom query to find cards by UserStory ID
    @Query("{ 'id' : ?0 }")
    List<Card> findCardsByUserStoryId(String userStoryId);

    List<UserStory> findBySprintId(String idSprint);
}
