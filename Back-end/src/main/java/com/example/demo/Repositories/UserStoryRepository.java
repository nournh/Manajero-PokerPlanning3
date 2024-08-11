package com.example.demo.Repositories;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.demo.Entity.Card;
import  com.example.demo.Entity.UserStory;

import java.util.List;
import java.util.Set;

public interface UserStoryRepository extends MongoRepository<UserStory, Long> {
    Set<UserStory> findUserStoryBySprintId(Long id);
//Rihab
    @Query("SELECT u.cardSet FROM UserStory u WHERE u.id = :userStoryId")
    List<Card> findCardsByUserStoryId(@Param("userStoryId") Long userStoryId);
    List<UserStory> findBySprintId(long idSprint);

}