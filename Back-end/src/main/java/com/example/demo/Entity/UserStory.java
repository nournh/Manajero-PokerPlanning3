package com.example.demo.Entity;

import com.example.demo.Entity.enums.Status;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.Set;

@Document(collection = "userStories")
public class UserStory {

    @Id
    private String id;

    @Field
    private String description;

    @Field
    private int duration;

    @Field
    private int priority;

    @Field
    private LocalDate startDate;

    @Field
    private LocalDate endDate;

    @Field
    private Status status;

    @DBRef
    private Sprint sprint;

    @DBRef
    private Set<Card> cardSet;

    // Default Constructor
    public UserStory() {}

    // Parameterized Constructor
    public UserStory(String description, int duration, int priority, LocalDate startDate, LocalDate endDate, Status status, Sprint sprint, Set<Card> cardSet) {
        this.description = description;
        this.duration = duration;
        this.priority = priority;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.sprint = sprint;
        this.cardSet = cardSet;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Sprint getSprint() {
        return sprint;
    }

    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
    }

    public Set<Card> getCardSet() {
        return cardSet;
    }

    public void setCardSet(Set<Card> cardSet) {
        this.cardSet = cardSet;
    }
}
