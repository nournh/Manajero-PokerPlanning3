package com.example.demo.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.Set;

@Document(collection = "sprint")
public class Sprint {

    @Id
    private String id;

    @Field
    private String name;

    @Field
    private LocalDate startDate;

    @Field
    private LocalDate endDate;

    @Field
    private int duration;

    @Field
    private String objective;

    @DBRef
    private Project project;

    @DBRef
    private Session session;

    @DBRef
    private Set<UserStory> userStorySet;

    // Default Constructor
    public Sprint() {}

    // Parameterized Constructor
    public Sprint(String name, LocalDate startDate, LocalDate endDate, int duration, String objective,
                  Project project, Session session, Set<UserStory> userStorySet) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
        this.objective = objective;
        this.project = project;
        this.session = session;
        this.userStorySet = userStorySet;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Set<UserStory> getUserStorySet() {
        return userStorySet;
    }

    public void setUserStorySet(Set<UserStory> userStorySet) {
        this.userStorySet = userStorySet;
    }
}
