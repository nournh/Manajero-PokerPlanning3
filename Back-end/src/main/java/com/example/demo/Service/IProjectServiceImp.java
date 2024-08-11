package com.example.demo.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.Entity.Project;
import com.example.demo.Entity.User;
import com.example.demo.Entity.enums.Domain;
import com.example.demo.Entity.enums.Role;
import com.example.demo.Entity.enums.StatusProject;
import com.example.demo.Repositories.ProjectRepository;
import com.example.demo.Repositories.UserRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class IProjectServiceImp implements IProjectService {
    public final ProjectRepository projectRepository;
    public final UserRepository userRepository;

    @Override
    public Project addProjectAndAssignProjectToUser(Project p, long id) {

        projectRepository.save(p);
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id"));
        // Vérifier si le Set de projets de l'utilisateur est null, puis l'initialiser si nécessaire
        if (user.getProjectSet() == null) {
            user.setProjectSet(new HashSet<>());
        }
        // Vérifier si le Set d'utilisateurs du projet est null, puis l'initialiser si nécessaire
        if (p.getUserSet() == null) {
            p.setUserSet(new HashSet<>());
        }
        //Ajouter l'utilisateur au set des users de project
        p.getUserSet().add(user);
        //Ajouter le projet au set des projets de l'utilisateur
        user.getProjectSet().add(p);
        //Sauvegarder les changements
        userRepository.save(user);
        return projectRepository.save(p);


    }

   /* @Override
    public List<Project> getAllProjetcs() {
        return projectRepository.findAll();
    }*/

    @Override
    public List<Project> getAllProjetcs() {
        List<Project> projectsList = projectRepository.findAll();
        List<Project> filteredProjects = new ArrayList<>();

        for (Project project : projectsList) {
            if (project.getBadget() != 0) { // Filtrer les projets dont le budget est différent de zéro
                filteredProjects.add(project);
            }
        }

        return filteredProjects;
    }


    @Override
    public List<Project> getProjectsByUserId(Long id) {
        /*Set<Project> projectsSet = projectRepository.findByUserSetId(id);
        List<Project> projectsList = new ArrayList<>(projectsSet);
        return projectsList;*/
        Set<Project> projectsSet = projectRepository.findByUserSetId(id);
        List<Project> projectsList = new ArrayList<>();

        for (Project project : projectsSet) {
            if (project.getBadget() != 0) { // Filtrer les projets dont le budget est différent de zéro
                projectsList.add(project);
            }
        }

        return projectsList;
    }

    /* @Override
     public void deleteProjectById(Long id) {

         projectRepository.deleteById(id);
     }*/
    @Override
    public void deleteProjectById(Long id) {
        Optional<Project> optionalProject = projectRepository.findById(id);
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            // Supprimer l'affectation de ce projet à tous les utilisateurs associés
            for (User user : project.getUserSet()) {
                user.getProjectSet().remove(project);
            }
            // Supprimer le projet
            projectRepository.deleteById(id);
        } else {
            // Gérer le cas où le projet n'est pas trouvé
            throw new NoSuchElementException("Project with id " + id + " not found");
        }
    }

    @Override
    public Project updateProject(Project p) {
        return projectRepository.save(p);
    }

    @Override
    public Project findById(long id) {
        return projectRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("project does not exist!"));
    }

    @Transactional
    @Override
    public Project assignProjectToDevelopers(Long idProject, List<Long> idDevelopers) {
        Project p = projectRepository.findById(idProject).orElseThrow(() -> new IllegalArgumentException("Project does not exist!"));
        {
            if(p.getNbDeveloper()>=idDevelopers.size()){
                for (Long idDeveloper : idDevelopers) {

                    User u = userRepository.findById(idDeveloper).orElseThrow(() -> new IllegalArgumentException("User does not exist!"));
                   // if (u.getRole().equals("DEVELOPER")) {
                        u.getProjectSet().add(p);
                  //  } else {
                      //  throw new IllegalArgumentException("you must assign the project to a developer!");
                   // }

                }
            }
            else {
                throw new IllegalArgumentException("you must assign the project to "+p.getNbDeveloper()+" developers!");
            }

            return p;


        }
    }

   /* @Override
    public List<User> getAllDevelopers() {
        return userRepository.findByRole(Role.DEVELOPER);
    }*/

    @Override
    public List<User> getAllDevelopers(Long id) {
        return userRepository.findByRoleAndNotAssignedToProject(Role.DEVELOPER, id);
    }
    @Override
    public List<User> getDevelopersByProjectId(Long id) {
        return userRepository.findByProjectSetIdAndRole(id, Role.DEVELOPER);
    }

    @Override
    public int countProjectsByStatus(StatusProject status) {
        return projectRepository.countProjectBystatusProject(status);
    }

    @Override
    public List<Project> searchProjects(Integer badget, LocalDate startDate, LocalDate endDate, StatusProject status
            , Domain domain,String name,Integer nbDeveloper) {
      //return projectRepository.findProjectsByCriteria(badget,startDate,endDate,status,domain,name,nbDeveloper);
        // Logique de filtrage des projets en fonction des critères de recherche
        List<Project> projects = getAllProjetcs(); // Suppose que vous avez une méthode pour obtenir tous les projets
        List<Project> filteredProjects = new ArrayList<>();

        for (Project project : projects) {
            if ((badget == null || project.getBadget()==(badget)) &&
                    (startDate == null || project.getStartDate().equals(startDate)) &&
                    (endDate == null || project.getEndDate().equals(endDate)) &&
                    (status == null || project.getStatusProject().equals(status)) &&
                    (domain == null || project.getDomain().equals(domain)) &&
                    (name == null || project.getName().contains(name)) &&
                    (nbDeveloper == null || project.getNbDeveloper()==(nbDeveloper))) {
                filteredProjects.add(project);
            }
        }

        return filteredProjects;
    }

    public void importProjectFromExcel(MultipartFile file, Long userId) {
        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0); // Assuming project data is in the first sheet

            String projectName = sheet.getRow(3).getCell(4).getStringCellValue(); // Assuming Project Name is in column 5

            // Create the project
            Project project = new Project();
            project.setName(projectName);

            // Save the project first
            project = projectRepository.save(project);  // Persist the project

            // Retrieve the user from the database
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid user Id"));

            // Associate the user with the project (project is now saved)
            if (user.getProjectSet() == null) {
                user.setProjectSet(new HashSet<>());
            }
            user.getProjectSet().add(project);

            // Persist the user (association is established)
            userRepository.save(user);

            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to import project from Excel: " + e.getMessage());
        }
    }

    @Override
    public List<Project> getImportedProjects(Long userId) {
        return projectRepository.findByUserSetIdAndBadgetEquals(userId, 0);
    }






    /*public void importProjectFromExcel(MultipartFile file,Long userId) {
        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0); // Assuming project data is in the first sheet

            String projectName = sheet.getRow(3).getCell(4).getStringCellValue(); // Assuming Project Name is in column 5

            Project project = new Project();
            project.setName(projectName);

            // Associez le projet à l'utilisateur correspondant
            // Associez le projet à l'utilisateur correspondant
            User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user Id"));

                if (project.getUserSet() == null) {
                    project.setUserSet(new HashSet<>());
                }
                project.getUserSet().add(user);
                if (user.getProjectSet() == null) {
                    user.setProjectSet(new HashSet<>());
                }
                user.getProjectSet().add(project);


            userRepository.save(user);
            projectRepository.save(project);

            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to import project from Excel: " + e.getMessage());
        }
    }*/



}
