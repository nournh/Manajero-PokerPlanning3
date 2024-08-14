package tn.esprit.pockerplanning.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import tn.esprit.pockerplanning.entities.Activity;

public interface ActivityRepository extends MongoRepository<Activity, String> {
}
