package com.example.demo.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Entity.Sprint;
import com.example.demo.Service.ISprintService;

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
    public Sprint addSprintAndAssignSprintToProject(@RequestBody Sprint s,@PathVariable long id) {
        return iSprintService.addSprintAndAssignSprintToProject(s,id);
    }
    @GetMapping("/getSprintsByProjectId/{id}")
    public List<Sprint> getSprintsByProjectId(@PathVariable Long id) {
        return iSprintService.getSprintsByProjectId(id);
    }
    @DeleteMapping("/deleteSprint/{id}")
    public void deleteSprintById(@PathVariable Long id) {
        iSprintService.deleteSprintById(id);
    }
    @PutMapping("/updateSprint")
    public Sprint updateSprint(@RequestBody Sprint p) {
        return iSprintService.updateSprint(p);
    }
    @GetMapping("/getSprintById/{id}")
    public Sprint findById(@PathVariable long id) {
        return iSprintService.findById(id);
    }
}
