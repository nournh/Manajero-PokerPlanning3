package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import  com.example.demo.Entity.enums.Role;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "users")
public class User implements Serializable {

    @Id
    String id; // MongoDB uses String for IDs (typically ObjectId)

    String firstname;
    String lastname;

    String email; // Ensure unique constraint at the application or database level
    String password;
    String picture;

    Role role;

    @JsonIgnore
    @DBRef(lazy = true)
    Set<Project> projectSet;





    String passwordResetToken;
    Boolean verified;
    String activationToken;
    Boolean active;





    public String getPassword() {
        return password;
    }


    public String getUsername() {
        return email;
    }


    public boolean isAccountNonExpired() {
        return true;
    }


    public boolean isAccountNonLocked() {
        return true;
    }


    public boolean isCredentialsNonExpired() {
        return true;
    }


    public boolean isEnabled() {
        return active; // Assuming `active` determines if the account is enabled
    }
}
