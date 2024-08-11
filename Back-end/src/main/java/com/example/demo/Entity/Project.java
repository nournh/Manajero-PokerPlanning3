package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import  com.example.demo.Entity.enums.Domain;
import  com.example.demo.Entity.enums.StatusProject;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "projects") // Specifies the MongoDB collection name
public class Project {

    @Id
    @Setter(AccessLevel.NONE)
    String id; // MongoDB uses String for IDs (typically ObjectId)

    String name;
    LocalDate startDate;
    LocalDate endDate;
    int nbDeveloper;
    String objective;
    String technology;
    int budget; // Corrected field name from 'badget' to 'budget'

    @Field("domain")
    Domain domain;

    @Field("status_project")
    StatusProject statusProject;

    @JsonIgnore
    @DBRef(lazy = true) // Reference to User documents
    Set<User> userSet;



    @JsonIgnore
    @DBRef(lazy = true) // Reference to Sprint documents
    Set<Sprint> sprintSet;
}
