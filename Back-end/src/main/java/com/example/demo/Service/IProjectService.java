package com.example.demo.Service;

import com.example.demo.Entity.Project;
import com.example.demo.Entity.User;
import com.example.demo.Entity.enums.Domain;
import com.example.demo.Entity.enums.StatusProject;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public interface IProjectService {
    Project addProjectAndAssignProjectToUser(Project p , long id);
    List<Project> getAllProjetcs();
    List<Project> getProjectsByUserId(Long id);

    void deleteProjectById(Long id);
    Project updateProject(Project p);
    Project findById(long id);
    Project assignProjectToDevelopers(Long idProject, List<Long> idDevelopers);

    List<User> getAllDevelopers(Long id);

    List<User>getDevelopersByProjectId(Long id);
    int countProjectsByStatus(StatusProject status);
    List<Project> searchProjects(Integer badget, LocalDate startDate,
                                 LocalDate endDate, StatusProject status, Domain domain,String name,Integer nbDeveloper);
    void importProjectFromExcel(MultipartFile file,Long userId);
    List<Project> getImportedProjects(Long userId);
}
