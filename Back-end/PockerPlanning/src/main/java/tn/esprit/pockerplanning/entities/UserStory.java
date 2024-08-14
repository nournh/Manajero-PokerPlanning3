package tn.esprit.pockerplanning.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;
import lombok.experimental.FieldDefaults;
import tn.esprit.pockerplanning.entities.enums.Complexity;
import tn.esprit.pockerplanning.entities.enums.StatusUs;

import java.time.LocalDate;
import java.util.Set;

@Document(collection = "userStories") // Marks the class as a MongoDB document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserStory {

    @Id // MongoDB uses this annotation for the primary key
    @Setter(AccessLevel.NONE)
    private String id; // Change to String, as MongoDB typically uses ObjectId as the ID

    private String description;
    private int duration;
    private int priority;
    private LocalDate startDate;
    private LocalDate endDate;


    private Complexity finalcomplexity;


    private StatusUs status;

    @DBRef // References another document (Sprint)
    private Sprint sprint;

    @DBRef // References other documents (Note)
    private Set<Note> noteSet;

    @DBRef // References other documents (Card)
    private Set<Card> cardSet;

    // Methods to retrieve notes and cards for a user
    public String getUserNoteForUserStory(String idUser) {
        for (Note note : noteSet) {
            if (note.getIdUser().equals(idUser)) {
                return note.getDescription();
            }
        }
        return null;
    }

    public Note getNoteSet(String idUser) {
        for (Note note : noteSet) {
            if (note.getIdUser().equals(idUser)) {
                return note;
            }
        }
        return null;
    }

    public int getUserEstimationForUserStory(String idUser) {
        for (Card card : cardSet) {
            if (card.getIdUser().equals(idUser)) {
                return card.getComplexity().getValue();
            }
        }
        return -1;
    }

    public Card getCardSet(String idUser) {
        for (Card card : cardSet) {
            if (card.getIdUser().equals(idUser)) {
                return card;
            }
        }
        return null;
    }


    public void setUserStoryId(String userStoryId) {
        this.id = userStoryId;
    }

    public String getUserStoryId() {
        return id;
    }

    public void setCardSetId(String cardSetId) {
        this.id = cardSetId;
    }
}
