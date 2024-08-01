package com.example.demo.Entity;

import com.example.demo.Entity.enums.Role;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Set;

@Document(collection = "user")
public class User {

    @Id
    private String id;

    @Field
    private String firstname;

    @Field
    private String lastname;

    @Field
    private String email;

    @Field
    private String password;

    @Field
    private String picture;

    @Field
    private Role role;

    @DBRef
    private Set<Project> projectSet;

    // Default Constructor
    public User() {}

    // Parameterized Constructor
    public User(String firstname, String lastname, String email, String password, String picture, Role role, Set<Project> projectSet) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.picture = picture;
        this.role = role;
        this.projectSet = projectSet;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<Project> getProjectSet() {
        return projectSet;
    }

    public void setProjectSet(Set<Project> projectSet) {
        this.projectSet = projectSet;
    }
}
