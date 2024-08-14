package tn.esprit.pockerplanning.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Document(collection = "evenements") // Marks the class as a MongoDB document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Evenement {

    @Id // MongoDB uses this annotation for the primary key
    @Setter(AccessLevel.NONE)
    private String id; // Change to String, as MongoDB typically uses ObjectId as the ID

    private String address;
    private LocalDate date;
    private String description;
    private String name;
    private int nbPlace;
    private String picture;
    private float price;
    private LocalTime hour;

    @DBRef // References to other documents (Supplier)
    private Set<Supplier> supplierSet;

    @DBRef // References to other documents (Participation)
    private Set<Participation> partcipationSet;

    @DBRef // References to other documents (Activity)
    private Set<Activity> activitySet;

}
