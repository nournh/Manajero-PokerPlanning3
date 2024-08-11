package com.example.demo.Service;

import com.example.demo.Entity.Sprint;

import java.util.List;

public interface ISprintService {
    Sprint addSprintAndAssignSprintToProject(Sprint s , long id);
    List<Sprint> getSprintsByProjectId(Long id);
    void deleteSprintById(Long id);
    Sprint updateSprint(Sprint p);
    Sprint findById(long id);
}
