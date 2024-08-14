package tn.esprit.pockerplanning.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Document(collection = "evaluations") // Marks the class as a MongoDB document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Evaluation {

    @Id // MongoDB uses this annotation for the primary key
    @Setter(AccessLevel.NONE)
    private String id; // Change to String, as MongoDB typically uses ObjectId as the ID

    private int pointsAwarded;
    private LocalDate dateAssigned;
    private LocalDate startDate;
    private LocalDate endDate;

    @DBRef // References another document (Badge)
    private Badge badge;
}
