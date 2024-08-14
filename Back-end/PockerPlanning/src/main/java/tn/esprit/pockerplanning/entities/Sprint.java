package tn.esprit.pockerplanning.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Document(collection = "sprints") // Marks the class as a MongoDB document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Sprint {

    @Id // MongoDB uses this annotation for the primary key
    @Setter(AccessLevel.NONE)
    private String id; // Change to String, as MongoDB typically uses ObjectId as the ID

    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private int duration;
    private String objective;

    @DBRef // References another document (Project)
    private Project project;

    @DBRef // References another document (Session)
    private Session session;

    @JsonIgnore
    @DBRef // References other documents (UserStory)
    private Set<UserStory> userStorySet;
}
