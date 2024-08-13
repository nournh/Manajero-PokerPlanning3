package com.example.demo.Repositories;

import com.example.demo.Entity.WhatIfSection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository("mongoUserRepository")
public interface WhatIfSectionRepository extends MongoRepository<WhatIfSection, String> {
}
