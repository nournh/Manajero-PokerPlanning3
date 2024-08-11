package com.example.demo.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import tn.esprit.pockerplanning.entities.enums.Complexity;
import tn.esprit.pockerplanning.entities.enums.StatusUs;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "user_stories")
public class UserStory {

    @Id
    @Setter(AccessLevel.NONE)
    String id; // MongoDB uses String for IDs

    String description;
    int duration;
    int priority;
    LocalDate startDate;
    LocalDate endDate;

    @Field("final_complexity")
    Complexity finalComplexity;

    @Field("status")
    StatusUs status;

    @DBRef(lazy = true)
    Sprint sprint;

    @DBRef(lazy = true)
    Set<Note> noteSet;

    @DBRef(lazy = true)
    Set<Card> cardSet;

    // Methods for accessing notes and cards
    public String getUserNoteForUserStory(long idUser) {
        for (Note note : noteSet) {
            if (note.getIdUser() == idUser) {
                return note.getDescription();
            }
        }
        return null;
    }

    public Note getNoteSet(long idUser) {
        for (Note note : noteSet) {
            if (note.getIdUser() == idUser) {
                return note;
            }
        }
        return null;
    }

    public int getUserEstimationForUserStory(long idUser) {
        for (Card card : cardSet) {
            if (card.getIdUser() == idUser) {
                return card.getComplexity().getValue();
            }
        }
        return -1;
    }

    public Card getCardSet(long idUser) {
        for (Card card : cardSet) {
            if (card.getIdUser() == idUser) {
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
