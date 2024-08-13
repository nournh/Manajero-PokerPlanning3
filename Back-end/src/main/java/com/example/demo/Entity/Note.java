package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "notes") // Specifies the MongoDB collection name
public class Note {

    @Id
    @Setter(AccessLevel.NONE)
    long id; // MongoDB uses String for IDs (typically ObjectId)

    @Field("id_user") // Maps the field to "id_user" in MongoDB
    long idUser;

    String description;

    LocalDate date;

    @JsonIgnore
    @DBRef // Refers to another MongoDB document (UserStory)
  UserStory userStory;
}

