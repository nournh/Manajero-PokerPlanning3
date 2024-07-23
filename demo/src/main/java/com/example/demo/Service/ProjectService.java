package com.example.demo.Service;



import com.example.demo.Entity.Project;
import com.example.demo.Entity.User;
import com.example.demo.Entity.WhatSection;
import com.example.demo.Repositories.ProjectRepository;
import com.example.demo.Repositories.UserRepository;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
    private UserRepository userRepository;

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
    public void importProjectFromExcel(MultipartFile file, String userId) {
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

}

