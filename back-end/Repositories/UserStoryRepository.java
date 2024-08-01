package com.example.demo.Repositories;

import com.example.demo.Entity.Project;
import com.example.demo.Entity.UserStory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserStoryRepository extends MongoRepository<UserStory, String> {
}
