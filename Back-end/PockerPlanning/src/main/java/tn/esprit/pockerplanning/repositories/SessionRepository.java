package tn.esprit.pockerplanning.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
import tn.esprit.pockerplanning.entities.Session;

public interface SessionRepository extends MongoRepository<Session, String> {
}