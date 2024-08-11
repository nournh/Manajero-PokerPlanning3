package com.example.demo.Repositories;

import  com.example.demo.Entity.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TicketRepository extends MongoRepository<Ticket, Long> {
    List<Ticket> findByProjectId(Long projectId);
}