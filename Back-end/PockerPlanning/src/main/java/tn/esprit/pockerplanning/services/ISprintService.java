package tn.esprit.pockerplanning.services;


import tn.esprit.pockerplanning.entities.Project;
import tn.esprit.pockerplanning.entities.Sprint;

import java.util.List;

public interface ISprintService {
    Sprint addSprintAndAssignSprintToProject(Sprint s , String id);
    List<Sprint> getSprintsByProjectId(String id);
    void deleteSprintById(String id);
    Sprint updateSprint(Sprint p);
    Sprint findById(String id);
}
