package tn.esprit.pockerplanning.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
import tn.esprit.pockerplanning.entities.Ticket;

import java.util.List;

public interface TicketRepository extends MongoRepository<Ticket, String> {
    List<Ticket> findByProjectId(Long projectId);
}