package com.example.demo.Service;



import com.example.demo.Entity.Project;
import com.example.demo.Entity.WhatSection;
import com.example.demo.Repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    // Create
    public Project createProject(Project project) {
        return projectRepository.save(project);
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

