package tn.esprit.pockerplanning.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pockerplanning.entities.Ticket;
import tn.esprit.pockerplanning.services.ITicketService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:53563", allowedHeaders = "*")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@RequestMapping("ticket")
public class TicketController {
    public final ITicketService iTicketService;
    @PostMapping("/importTickets/{projectId}")
    public void importTicketsFromExcel(@RequestParam("file") MultipartFile file,@PathVariable String projectId) {
        iTicketService.importTicketsFromExcel(file,projectId);
    }

    @GetMapping("/getTicketsByProjectId/{id}")
    public List<Ticket> getTicketsByProjectId(@PathVariable String id) {
        return iTicketService.getTicketsByProjectId(id);
    }
}
