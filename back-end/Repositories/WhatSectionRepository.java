package com.example.demo.Repositories;

import com.example.demo.Entity.WhatSection;
import com.example.demo.Entity.WhySection;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WhatSectionRepository extends MongoRepository<WhatSection, String> {
}
