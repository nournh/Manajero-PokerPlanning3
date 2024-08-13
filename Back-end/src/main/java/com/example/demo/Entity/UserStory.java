package com.example.demo.Entity;

import com.example.demo.Entity.enums.Complexity;
import com.example.demo.Entity.enums.StatusUs;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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
    long id; // MongoDB uses String for IDs

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

    public void setFinalcomplexity(Complexity complexity) {
    }

    public <U> U getFinalcomplexity() {
        return getFinalcomplexity();
    }

    // Methods for accessing notes and cards







}
