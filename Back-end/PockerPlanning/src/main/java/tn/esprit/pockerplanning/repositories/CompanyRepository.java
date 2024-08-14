package tn.esprit.pockerplanning.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
import tn.esprit.pockerplanning.entities.Company;

public interface CompanyRepository extends MongoRepository<Company, String> {
}