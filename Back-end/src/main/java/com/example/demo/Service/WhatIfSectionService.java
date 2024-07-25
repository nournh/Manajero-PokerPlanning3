package com.example.demo.Service;

import com.example.demo.Entity.WhatIfSection;
import com.example.demo.Repositories.WhatIfSectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WhatIfSectionService {
    @Autowired
    private WhatIfSectionRepository repository;

    public List<WhatIfSection> getAllSections() {
        return repository.findAll();
    }

    public Optional<WhatIfSection> getSectionById(String id) {
        return repository.findById(id);
    }

    public WhatIfSection createSection(WhatIfSection section) {
        return repository.save(section);
    }

    public Optional<WhatIfSection> updateSection(String id, WhatIfSection section) {
        Optional<WhatIfSection> existingSection = repository.findById(id);
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
