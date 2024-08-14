package tn.esprit.pockerplanning.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import tn.esprit.pockerplanning.entities.Participation;

public interface ParticipationRepository extends MongoRepository<Participation, String> {
}