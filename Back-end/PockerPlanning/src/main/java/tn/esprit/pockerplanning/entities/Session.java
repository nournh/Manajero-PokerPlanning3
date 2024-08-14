package tn.esprit.pockerplanning.entities;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import lombok.*;
import lombok.experimental.FieldDefaults;
import tn.esprit.pockerplanning.entities.enums.CurrentState;

import java.time.LocalDate;

@Document(collection = "sessions") // Marks the class as a MongoDB document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Session {

    @Id // MongoDB uses this annotation for the primary key
    @Setter(AccessLevel.NONE)
    private String id; // Change to String, as MongoDB typically uses ObjectId as the ID

    private LocalDate startDate;
    private LocalDate endDate;
    private int duration;


    private CurrentState currentState;

    @DBRef // References another document (Sprint)
    private Sprint sprint;
}
