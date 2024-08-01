package com.example.demo.Service;

import com.example.demo.Entity.WhySection;
import com.example.demo.Repositories.WhySectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WhySectionService {

    @Autowired
    private WhySectionRepository repository;

    // Method to retrieve all WhySections
    public List<WhySection> getAllSections() {
        return repository.findAll();
    }

    // Method to retrieve a WhySection by its ID
    public Optional<WhySection> getSectionById(String id) {
        return repository.findById(id);
    }

    // Method to create a new WhySection
    public WhySection createSection(WhySection section) {
        return repository.save(section);
    }

    // Method to update an existing WhySection by its ID
    public Optional<WhySection> updateSection(String id, WhySection section) {
        Optional<WhySection> existingSection = repository.findById(id);
        if (existingSection.isPresent()) {
            section.setId(id); // Ensure the ID is set to update the correct document
            return Optional.of(repository.save(section));
        } else {
            return Optional.empty(); // Return empty if the section doesn't exist
        }
    }

    // Method to delete a WhySection by its ID
    public boolean deleteSection(String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true; // Return true if deletion is successful
        }
        return false; // Return false if the section doesn't exist
    }
}
