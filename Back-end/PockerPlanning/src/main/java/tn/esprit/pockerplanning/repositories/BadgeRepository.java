package tn.esprit.pockerplanning.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import tn.esprit.pockerplanning.entities.Badge;

public interface BadgeRepository extends MongoRepository<Badge, String> {
}
