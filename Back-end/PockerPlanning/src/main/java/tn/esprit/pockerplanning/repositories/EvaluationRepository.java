package tn.esprit.pockerplanning.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import tn.esprit.pockerplanning.entities.Evaluation;

public interface EvaluationRepository extends MongoRepository<Evaluation, String> {
}