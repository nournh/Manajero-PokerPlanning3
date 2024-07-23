package com.example.demo.Repositories;



import com.example.demo.Entity.Card;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface CardRepository extends MongoRepository<Card, String> {
}
