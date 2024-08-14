package tn.esprit.pockerplanning.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;

@Document(collection = "activities") // This annotation marks the class as a MongoDB document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Activity {

    @Id // MongoDB uses this annotation for the primary key
    @Setter(AccessLevel.NONE)
    private String id; // Change type to String, as MongoDB typically uses ObjectId as the ID

    private String description;
    private LocalTime hour;
    private String name;
    private int nbPerson;

    @DBRef // Use this to create a reference to another document (Evenement)
    private Evenement evenement;
}
