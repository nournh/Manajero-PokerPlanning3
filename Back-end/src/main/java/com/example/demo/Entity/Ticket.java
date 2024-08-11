package com.example.demo.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "tickets") // Specifies the MongoDB collection name
public class Ticket {

    @Id
    @Setter(AccessLevel.NONE)
    String id; // MongoDB uses String for IDs (typically ObjectId)

    String description;
    String status;

    @JsonIgnore
    @DBRef(lazy = true) // Reference to the Project document
    Project project;
}
