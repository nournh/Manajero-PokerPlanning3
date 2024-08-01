package com.example.demo.Entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = " Avantages")
public class  Avantages {

    @Id
    private String id;


    private String description;
    private boolean published;

    public  Avantages() {
    }

    public Avantages( String description) {

        this.description = description;
        this.published = published;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }
    // Getters and Setters
}
