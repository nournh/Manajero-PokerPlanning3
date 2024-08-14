package tn.esprit.pockerplanning.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Document(collection = "badges") // This marks the class as a MongoDB document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Badge {

    @Id // MongoDB uses this annotation for the primary key
    @Setter(AccessLevel.NONE)
    private String id; // Change to String, as MongoDB typically uses ObjectId as the ID

    private int points;
    private LocalDate date;
    private String description;

    @DBRef // References to other documents (users associated with this badge)
    private Set<User> userSet;

    @DBRef // References to evaluations associated with this badge
    private Set<Evaluation> evaluationSet;
}
