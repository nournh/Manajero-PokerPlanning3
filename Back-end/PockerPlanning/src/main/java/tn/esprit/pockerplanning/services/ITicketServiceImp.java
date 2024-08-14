package tn.esprit.pockerplanning.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pockerplanning.entities.Project;
import tn.esprit.pockerplanning.entities.Ticket;
import tn.esprit.pockerplanning.repositories.ProjectRepository;
import tn.esprit.pockerplanning.repositories.TicketRepository;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ITicketServiceImp implements ITicketService {
    private final TicketRepository ticketRepository;
    private final ProjectRepository projectRepository;
    public void importTicketsFromExcel(MultipartFile file, String projectId) {
        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0); // Assuming ticket data is in the first sheet

            // Retrieve the project from the database
            Project project = projectRepository.findById(projectId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid project Id"));

            // Import tickets from row 4 to the last filled row
            for (int rowIndex = 3; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);

                // Assuming the columns are in the order: UnitoId, Issue ID, Description, Status, Project Name, Summary, Start Date
                String description = row.getCell(5).getStringCellValue();
                String status = row.getCell(3).getStringCellValue();

                // Create the ticket
                Ticket ticket = new Ticket();
                ticket.setDescription(description);
                ticket.setStatus(status);
                ticket.setProject(project); // Associate ticket with the project

                // Save the ticket
                ticketRepository.save(ticket);
            }

            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to import tickets from Excel: " + e.getMessage());
        }
    }

    @Override
    public List<Ticket> getTicketsByProjectId(String id) {
        return ticketRepository.findByProjectId(Long.valueOf(id));
    }

}
