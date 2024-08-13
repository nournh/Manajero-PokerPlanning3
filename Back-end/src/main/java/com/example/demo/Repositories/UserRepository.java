package com.example.demo.Repositories;

import com.example.demo.Entity.User;
import com.example.demo.Entity.enums.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);
    User findByActivationToken(String token);
    Optional<User> findByPasswordResetToken(String passwordResetToken);

    @Query("{ 'role': 'PROJECTMANAGER' }")
    List<User> findprojectmanager();

    @Query("{ 'role': 'DEVELOPER' }")
    List<User> finddeveloppeur();

    @Query("{ 'role': :#{#role}, 'projectSet.id': { $ne: :#{#projectId} } }")
    List<User> findByRoleAndNotAssignedToProject(@Param("role") Role role, @Param("projectId") Long projectId);

    List<User> findByProjectSetIdAndRole(long id, Role role);

    @Query("{ 'role': 'DEVELOPER' }")
    List<User> findDevelopersWithCards();

    @Query("{ 'role': 'DEVELOPER' }")
    List<User> findDevelopersWithNotes();
}
