package tn.esprit.pockerplanning.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tn.esprit.pockerplanning.entities.enums.Role;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Document(collection = "users") // Marks the class as a MongoDB document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User implements Serializable, UserDetails {

    @Id // MongoDB uses this annotation for the primary key
    @Setter(AccessLevel.NONE)
    private String id; // Change to String, as MongoDB typically uses ObjectId as the ID

    private String firstname;
    private String lastname;

    private String email;
    private String password;
    private String picture;


    private Role role;

    @JsonIgnore
    @DBRef // References other documents (Project)
    private Set<Project> projectSet;

    @JsonIgnore
    @DBRef // References other documents (Participation)
    private Set<Participation> participationSet;

    @JsonIgnore
    @DBRef // References another document (Badge)
    private Badge badge;

    @JsonIgnore
    @DBRef // References another document (Company)
    private Company company;

    private String passwordResetToken;

    private Boolean verified;

    private String activationToken;

    private Boolean active;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setId(String id) {
    }
}
