package tn.esprit.pockerplanning.services;

import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pockerplanning.entities.Ticket;

import java.util.List;

public interface ITicketService {
   void importTicketsFromExcel(MultipartFile file, String projectId);
   List<Ticket> getTicketsByProjectId(String id);
}
