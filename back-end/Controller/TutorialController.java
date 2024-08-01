package com.example.demo.Controller;

import com.example.demo.Entity.WhatIfSection;
import com.example.demo.Entity.WhySection;
import com.example.demo.Entity.WhatSection;
import com.example.demo.Service.WhatIfSectionService;
import com.example.demo.Service.WhySectionService;
import com.example.demo.Service.WhatSectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class TutorialController {

    @Autowired
    private WhySectionService whySectionService;

    @Autowired
    private WhatSectionService whatSectionService;

    @Autowired
    private WhatIfSectionService whatIfSectionService;

    // CRUD operations for WhySection
    @GetMapping("/why-sections")
    public ResponseEntity<List<WhySection>> getAllWhySections() {
        List<WhySection> whySections = whySectionService.getAllSections();
        return ResponseEntity.ok(whySections);
    }

    @GetMapping("/why-sections/{id}")
    public ResponseEntity<WhySection> getWhySectionById(@PathVariable String id) {
        Optional<WhySection> whySection = whySectionService.getSectionById(id);

        if (whySection.isPresent()) {
            return ResponseEntity.ok(whySection.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/why-sections")

    public ResponseEntity<WhySection> createWhySection(@RequestBody WhySection whySection) {
        WhySection createdWhySection = whySectionService.createSection(whySection);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWhySection);
    }

    @PutMapping("/why-sections/{id}")
    public ResponseEntity<WhySection> updateWhySection(@PathVariable String id, @RequestBody WhySection whySection) {
        Optional<WhySection> updatedWhySection = whySectionService.updateSection(id, whySection);

        if (updatedWhySection.isPresent()) {
            return ResponseEntity.ok(updatedWhySection.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/why-sections/{id}")
    public ResponseEntity<Void> deleteWhySection(@PathVariable String id) {
        boolean isDeleted = whySectionService.deleteSection(id);

        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // CRUD operations for WhatSection
    @GetMapping("/what-sections")
    public ResponseEntity<List<WhatSection>> getAllWhatSections() {
        List<WhatSection> whatSections = whatSectionService.getAllSections();
        return ResponseEntity.ok(whatSections);
    }

    @GetMapping("/what-sections/{id}")
    public ResponseEntity<WhatSection> getWhatSectionById(@PathVariable String id) {
        Optional<WhatSection> whatSection = whatSectionService.getSectionById(id);

        if (whatSection.isPresent()) {
            return ResponseEntity.ok(whatSection.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/what-sections")
    public ResponseEntity<WhatSection> createWhatSection(@RequestBody WhatSection whatSection) {
        WhatSection createdWhatSection = whatSectionService.createSection(whatSection);
        return ResponseEntity.ok(createdWhatSection);
    }

    @PutMapping("/what-sections/{id}")
    public ResponseEntity<WhatSection> updateWhatSection(@PathVariable String id, @RequestBody WhatSection whatSection) {
        Optional<WhatSection> updatedWhatSection = whatSectionService.updateSection(id, whatSection);

        if (updatedWhatSection.isPresent()) {
            return ResponseEntity.ok(updatedWhatSection.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/what-sections/{id}")
    public ResponseEntity<Void> deleteWhatSection(@PathVariable String id) {
        boolean isDeleted = whatSectionService.deleteSection(id);

        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // CRUD operations for WhatIfSection
    @GetMapping("/what-if-sections")
    public ResponseEntity<List<WhatIfSection>> getAllWhatIfSections() {
        List<WhatIfSection> whatIfSections = whatIfSectionService.getAllSections();
        return ResponseEntity.ok(whatIfSections);
    }

    @GetMapping("/what-if-sections/{id}")
    public ResponseEntity<WhatIfSection> getWhatIfSectionById(@PathVariable String id) {
        Optional<WhatIfSection> whatIfSection = whatIfSectionService.getSectionById(id);

        if (whatIfSection.isPresent()) {
            return ResponseEntity.ok(whatIfSection.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/what-if-sections")
    public ResponseEntity<WhatIfSection> createWhatIfSection(@RequestBody WhatIfSection whatIfSection) {
        WhatIfSection createdWhatIfSection = whatIfSectionService.createSection(whatIfSection);
        return ResponseEntity.ok(createdWhatIfSection);
    }

    @PutMapping("/what-if-sections/{id}")
    public ResponseEntity<WhatIfSection> updateWhatIfSection(@PathVariable String id, @RequestBody WhatIfSection whatIfSection) {
        Optional<WhatIfSection> updatedWhatIfSection = whatIfSectionService.updateSection(id, whatIfSection);

        if (updatedWhatIfSection.isPresent()) {
            return ResponseEntity.ok(updatedWhatIfSection.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/what-if-sections/{id}")
    public ResponseEntity<Void> deleteWhatIfSection(@PathVariable String id) {
        boolean isDeleted = whatIfSectionService.deleteSection(id);

        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
