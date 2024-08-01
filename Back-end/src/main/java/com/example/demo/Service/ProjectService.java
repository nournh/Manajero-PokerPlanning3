package com.example.demo.Service;



import com.example.demo.Entity.Project;
import com.example.demo.Entity.User;
import com.example.demo.Entity.WhatSection;
import com.example.demo.Repositories.ProjectRepository;
import com.example.demo.Repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;


    // Create
    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public Project addProjectAndAssignProjectToUser(Project p, String id) {

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
    // Read
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Optional<Project> getProjectById(String id) {
        return projectRepository.findById(id);
    }

    // Update
    public Project updateProject(String id, Project updatedProject) {
        if (projectRepository.existsById(id)) {
            updatedProject.setId(id); // Ensure ID is set
            return projectRepository.save(updatedProject);
        } else {
            return null;
        }
    }



    // Delete
    public void deleteProject(String id) {
        projectRepository.deleteById(id);
    }

}

