package com.example.demo.Repositories;


import com.example.demo.Entity.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectRepository extends MongoRepository<Project, String> {
    // Custom query methods if needed
}
