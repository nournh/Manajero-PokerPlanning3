package tn.esprit.pockerplanning.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
import tn.esprit.pockerplanning.entities.Sprint;

import java.util.Set;

public interface SprintRepository extends MongoRepository<Sprint, String> {
    Set<Sprint> findByProjectId(long project_id);
}