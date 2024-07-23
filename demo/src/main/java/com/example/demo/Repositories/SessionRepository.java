package com.example.demo.Repositories;

import com.example.demo.Entity.Project;
import com.example.demo.Entity.Session;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SessionRepository extends MongoRepository<Session, String> {
}
