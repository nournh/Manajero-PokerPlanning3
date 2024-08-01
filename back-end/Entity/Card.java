package com.example.demo.Entity;

import com.example.demo.Entity.enums.Complexity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "cards")
public class Card {

    @Id
    private String id;

    @Field
    private String color;

    @Field
    private Complexity complexity;

    // Constructors
    public Card() {}

    public Card(String color, Complexity complexity) {
        this.color = color;
        this.complexity = complexity;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Complexity getComplexity() {
        return complexity;
    }

    public void setComplexity(Complexity complexity) {
        this.complexity = complexity;
    }
}
