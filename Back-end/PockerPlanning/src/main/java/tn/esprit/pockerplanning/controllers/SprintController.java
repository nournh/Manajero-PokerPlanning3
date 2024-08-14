package tn.esprit.pockerplanning.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pockerplanning.entities.Sprint;
import tn.esprit.pockerplanning.services.ISprintService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
//@CrossOrigin(origins = "*")
//@CrossOrigin(origins = "http://localhost:53563", allowedHeaders = "*")
@RequestMapping("sprint")
public class SprintController {
    public final ISprintService iSprintService;
    @PostMapping("/addSAndAssign/{id}")
    public Sprint addSprintAndAssignSprintToProject(@RequestBody Sprint s,@PathVariable String id) {
        return iSprintService.addSprintAndAssignSprintToProject(s,id);
    }
    @GetMapping("/getSprintsByProjectId/{id}")
    public List<Sprint> getSprintsByProjectId(@PathVariable String id) {
        return iSprintService.getSprintsByProjectId(id);
    }
    @DeleteMapping("/deleteSprint/{id}")
    public void deleteSprintById(@PathVariable String id) {
        iSprintService.deleteSprintById(id);
    }
    @PutMapping("/updateSprint")
    public Sprint updateSprint(@RequestBody Sprint p) {
        return iSprintService.updateSprint(p);
    }
    @GetMapping("/getSprintById/{id}")
    public Sprint findById(@PathVariable String id) {
        return iSprintService.findById(id);
    }
}
