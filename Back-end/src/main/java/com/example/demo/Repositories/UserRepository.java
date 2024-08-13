package com.example.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.demo.Entity.User;
import com.example.demo.Entity.enums.Role;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    User findByActivationToken(String token);
    Optional<User> findByPasswordResetToken(String passwordResetToken);
    User findByRole(Role role);
    @Query("SELECT u FROM User u WHERE u.role = 'PROJECTMANAGER' ")
    List<User> findprojectmanager();


    @Query("SELECT u FROM User u WHERE u.role = 'DEVELOPER' ")
    List<User> finddeveloppeur();

    @Query("SELECT u FROM User u WHERE u.role = :role AND NOT EXISTS (SELECT p FROM u.projectSet p WHERE p.id = :projectId)")
    List<User> findByRoleAndNotAssignedToProject(@Param("role") Role role, @Param("projectId") Long projectId);
    List<User> findByProjectSetIdAndRole(long id, Role role);

//Rihab

   // List<User> findByRole(Role role);

    @Query("SELECT DISTINCT u FROM User u INNER JOIN FETCH u.projectSet p INNER JOIN FETCH p.sprintSet s INNER JOIN FETCH s.userStorySet us INNER JOIN FETCH us.cardSet c WHERE u.role = 'DEVELOPER'")
    List<User> findDevelopersWithCards();
    @Query("SELECT DISTINCT u FROM User u INNER JOIN FETCH u.projectSet p INNER JOIN FETCH p.sprintSet s INNER JOIN FETCH s.userStorySet us INNER JOIN FETCH us.noteSet c WHERE u.role = 'DEVELOPER'")
    List<User> findDevelopersWithNotes();
    @Query("SELECT DISTINCT u FROM User u WHERE u.role = 'CHEF_DE_PROJET'")
    User findChefsDeProjet();

}