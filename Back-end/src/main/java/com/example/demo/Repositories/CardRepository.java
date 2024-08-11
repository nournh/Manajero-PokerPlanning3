package com.example.demo.Repositories;

import  com.example.demo.Entity.Card;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CardRepository extends MongoRepository<Card, Long> {
    List<Card> findByIdUser(long idUser);

}