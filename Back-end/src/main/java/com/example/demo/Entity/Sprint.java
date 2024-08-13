package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "sprints") // Specifies the MongoDB collection name
public class Sprint {

    @Id
    @Setter(AccessLevel.NONE)
    long id; // MongoDB uses String for IDs (typically ObjectId)

    String name;
    LocalDate startDate;
    LocalDate endDate;
    int duration;
    String objective;

    @DBRef // Reference to the Project document
    Project project;

    @DBRef // Reference to the Session document
    Session session;

    @JsonIgnore
    @DBRef(lazy = true) // Reference to a set of UserStory documents
    Set<UserStory> userStorySet;

}
