package tn.esprit.pockerplanning.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
import tn.esprit.pockerplanning.entities.Evenement;

public interface EvenementRepository extends MongoRepository<Evenement, String> {
}