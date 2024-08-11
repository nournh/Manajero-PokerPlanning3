package com.example.demo.Repositories;


import  com.example.demo.Entity.Sprint;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Set;

public interface SprintRepository extends MongoRepository<Sprint, Long> {
    Set<Sprint> findByProjectId(long project_id);
}