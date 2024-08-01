package com.example.demo.Repositories;

import com.example.demo.Entity.Avantages;
import org.springframework.data.mongodb.repository.MongoRepository;



public interface AvantagesRepository extends MongoRepository<Avantages, String> {
    }