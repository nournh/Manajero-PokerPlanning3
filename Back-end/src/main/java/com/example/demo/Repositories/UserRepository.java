package com.example.demo.Repositories;


import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<tn.esprit.pockerplanning.entities.User, String> {
}
