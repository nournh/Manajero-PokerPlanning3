package com.example.demo.Service;

import org.springframework.web.multipart.MultipartFile;
import com.example.demo.Entity.Ticket;

import java.util.List;

public interface ITicketService {
   void importTicketsFromExcel(MultipartFile file, Long projectId);
   List<Ticket> getTicketsByProjectId(Long id);
}
