package tn.esprit.pockerplanning.services;

import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pockerplanning.entities.Project;
import tn.esprit.pockerplanning.entities.User;
import tn.esprit.pockerplanning.entities.enums.Domain;
import tn.esprit.pockerplanning.entities.enums.StatusProject;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface IProjectService {
    Project addProjectAndAssignProjectToUser(Project p , String id);
    List<Project> getAllProjetcs();
    List<Project> getProjectsByUserId(String id);

    void deleteProjectById(String id);
    Project updateProject(Project p);
    Project findById(String id);
    Project assignProjectToDevelopers(String idProject, List<String> idDevelopers);

    List<User> getAllDevelopers(String id);

    List<User>getDevelopersByProjectId(String id);
    int countProjectsByStatus(StatusProject status);
    List<Project> searchProjects(Integer badget, LocalDate startDate,
                                 LocalDate endDate, StatusProject status, Domain domain,String name,Integer nbDeveloper);
    void importProjectFromExcel(MultipartFile file,String userId);
    List<Project> getImportedProjects(String userId);
}
