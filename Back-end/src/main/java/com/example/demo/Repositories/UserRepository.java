package com.example.demo.Repositories;

import com.example.demo.Entity.Project;
import com.example.demo.Entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
