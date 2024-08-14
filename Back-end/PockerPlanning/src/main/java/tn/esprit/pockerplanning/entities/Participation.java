package tn.esprit.pockerplanning.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "participations") // Marks the class as a MongoDB document
public class Participation {

    @Id // MongoDB uses this annotation for the primary key
    @Setter(AccessLevel.NONE)
    private String id; // Change to String, as MongoDB typically uses ObjectId as the ID

    private String firstname;
    private String lastname;
    private int number;
    private String email;

    @DBRef // References another document (User)
    private User user;

    @DBRef // References another document (Evenement)
    private Evenement evenement;
}
