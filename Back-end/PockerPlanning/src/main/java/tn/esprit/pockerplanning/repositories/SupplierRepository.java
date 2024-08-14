package tn.esprit.pockerplanning.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import tn.esprit.pockerplanning.entities.Supplier;

public interface SupplierRepository extends MongoRepository<Supplier, String> {
}