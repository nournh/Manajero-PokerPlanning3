package com.example.demo.Repositories;

import com.example.demo.Entity.WhatIfSection;
import com.example.demo.Entity.WhatSection;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WhatIfSectionRepository extends MongoRepository<WhatIfSection, String> {
}
