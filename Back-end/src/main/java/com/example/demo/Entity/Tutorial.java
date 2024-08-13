package com.example.demo.Entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "tutorials")
public class Tutorial {

    @Id
    private long id;


    private String description;
    private boolean published;

    public Tutorial() {
    }

    public Tutorial( String description) {

        this.description = description;
        this.published = published;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
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
