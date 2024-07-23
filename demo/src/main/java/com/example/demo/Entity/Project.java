package com.example.demo.Entity;

import com.example.demo.Entity.enums.Domain;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.Set;

@Document(collection = "project")
public class Project {

    @Id
    private String id;

    @Field
    private String name;

    @Field
    private LocalDate startDate;

    @Field
    private LocalDate endDate;

    @Field
    private int nbDeveloper;

    @Field
    private String objective;

    @Field
    private String technology;

    @Field
    private int budget;

    @Field
    private Domain domain;

    @DBRef
    private Set<User> userSet;

    @DBRef
    private Set<Sprint> sprintSet;

    // Constructors
    public Project() {}

    public Project(String name, LocalDate startDate, LocalDate endDate, int nbDeveloper,
                   String objective, String technology, int budget, Domain domain,
                   Set<User> userSet, Set<Sprint> sprintSet) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.nbDeveloper = nbDeveloper;
        this.objective = objective;
        this.technology = technology;
        this.budget = budget;
        this.domain = domain;
        this.userSet = userSet;
        this.sprintSet = sprintSet;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getNbDeveloper() {
        return nbDeveloper;
    }

    public void setNbDeveloper(int nbDeveloper) {
        this.nbDeveloper = nbDeveloper;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public Set<User> getUserSet() {
        return userSet;
    }

    public void setUserSet(Set<User> userSet) {
        this.userSet = userSet;
    }

    public Set<Sprint> getSprintSet() {
        return sprintSet;
    }

    public void setSprintSet(Set<Sprint> sprintSet) {
        this.sprintSet = sprintSet;
    }
}
