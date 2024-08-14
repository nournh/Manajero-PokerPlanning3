package tn.esprit.pockerplanning.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Document(collection = "tickets") // Marks the class as a MongoDB document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Ticket {

    @Id // MongoDB uses this annotation for the primary key
    @Setter(AccessLevel.NONE)
    private String id; // Change to String, as MongoDB typically uses ObjectId as the ID

    private String description;
    private String status;

    @JsonIgnore
    @DBRef // References another document (Project)
    private Project project;
}
