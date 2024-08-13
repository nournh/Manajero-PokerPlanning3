package com.example.demo.Entity;

import com.example.demo.Entity.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String id;

    String firstname;
    String lastname;

    @Column(unique=true)
    String email;
    String password;
    String picture;

    @Enumerated(EnumType.STRING)
    Role role;





    @JsonIgnore
    @ManyToMany
    Set<Project> projectSet;


    String passwordResetToken;

    Boolean verified;

    String activationToken;


    Boolean Active;



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
        return true;
    }
}
