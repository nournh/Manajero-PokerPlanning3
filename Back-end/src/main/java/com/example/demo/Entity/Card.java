package com.example.demo.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import tn.esprit.pockerplanning.entities.enums.Complexity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "cards") // Specifies the MongoDB collection name
public class Card {

    @Id
    @Setter(AccessLevel.NONE)
    String id; // MongoDB uses String for IDs (typically ObjectId)

    @Field("id_user") // Maps the field to "id_user" in MongoDB
    long idUser;

    @Field("complexity") // Maps the enum to a string field in MongoDB
    Complexity complexity;
}

