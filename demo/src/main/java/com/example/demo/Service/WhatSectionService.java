package com.example.demo.Service;

import com.example.demo.Entity.WhatSection;
import com.example.demo.Repositories.WhatSectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WhatSectionService {

    @Autowired
    private WhatSectionRepository repository;

    public List<WhatSection> getAllSections() {
        return repository.findAll();
    }

    public Optional<WhatSection> getSectionById(String id) {
        return repository.findById(id);
    }

    public WhatSection createSection(WhatSection section) {
        return repository.save(section);
    }

    public Optional<WhatSection> updateSection(String id, WhatSection section) {
        Optional<WhatSection> existingSection = repository.findById(id);
        if (existingSection.isPresent()) {
            section.setId(id);
            return Optional.of(repository.save(section));
        } else {
            return Optional.empty();
        }
    }

    public boolean deleteSection(String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}

