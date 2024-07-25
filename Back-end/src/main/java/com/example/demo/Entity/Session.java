package com.example.demo.Entity;

import com.example.demo.Entity.enums.CurrentState;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.Set;

@Document(collection = "session")
public class Session {

    @Id
    private String id;

    @Field
    private LocalDate startDate;

    @Field
    private LocalDate endDate;

    @Field
    private int duration;

    @Field
    private CurrentState currentState;

    @DBRef
    private Sprint sprint;

    @DBRef
    private Message message;

    @DBRef
    private Estimation estimation;

    @DBRef
    private Set<Historique> historiqueSet;

    // Default Constructor
    public Session() {}

    // Parameterized Constructor
    public Session(LocalDate startDate, LocalDate endDate, int duration, CurrentState currentState,
                   Sprint sprint, Message message, Estimation estimation, Set<Historique> historiqueSet) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
        this.currentState = currentState;
        this.sprint = sprint;
        this.message = message;
        this.estimation = estimation;
        this.historiqueSet = historiqueSet;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public CurrentState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(CurrentState currentState) {
        this.currentState = currentState;
    }

    public Sprint getSprint() {
        return sprint;
    }

    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Estimation getEstimation() {
        return estimation;
    }

    public void setEstimation(Estimation estimation) {
        this.estimation = estimation;
    }

    public Set<Historique> getHistoriqueSet() {
        return historiqueSet;
    }

    public void setHistoriqueSet(Set<Historique> historiqueSet) {
        this.historiqueSet = historiqueSet;
    }
}
