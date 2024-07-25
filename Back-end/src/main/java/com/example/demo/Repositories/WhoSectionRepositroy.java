package com.example.demo.Repositories;

import com.example.demo.Entity.WhoSection;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface WhoSectionRepositroy extends MongoRepository<WhoSection, String> {
}
