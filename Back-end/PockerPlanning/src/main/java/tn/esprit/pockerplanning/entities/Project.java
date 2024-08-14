package tn.esprit.pockerplanning.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;
import lombok.experimental.FieldDefaults;
import tn.esprit.pockerplanning.entities.enums.Domain;
import tn.esprit.pockerplanning.entities.enums.StatusProject;

import java.time.LocalDate;
import java.util.Set;

@Document(collection = "projects") // Marks the class as a MongoDB document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Project {

    @Id // MongoDB uses this annotation for the primary key
    @Setter(AccessLevel.NONE)
    private String id; // Change to String, as MongoDB typically uses ObjectId as the ID

    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private int nbDeveloper;
    private String objective;
    private String technology;
    private int badget;


    private Domain domain;


    private StatusProject statusProject;

    @JsonIgnore
    @DBRef // References other documents (User)
    private Set<User> userSet;

    @DBRef // References other documents (Ticket)
    private Set<Ticket> ticketSet;

    @JsonIgnore
    @DBRef // References other documents (Sprint)
    private Set<Sprint> sprintSet;
}
