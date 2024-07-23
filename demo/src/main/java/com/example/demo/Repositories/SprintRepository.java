package com.example.demo.Repositories;

import com.example.demo.Entity.Project;
import com.example.demo.Entity.Sprint;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SprintRepository extends MongoRepository<Sprint, String> {
}
