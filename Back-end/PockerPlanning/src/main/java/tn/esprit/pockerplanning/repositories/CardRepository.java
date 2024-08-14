package tn.esprit.pockerplanning.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import tn.esprit.pockerplanning.entities.Card;

import java.util.List;

public interface CardRepository extends MongoRepository<Card, String> {
    List<Card> findByIdUser(String idUser); // Adjusted to match the type of idUser
}
