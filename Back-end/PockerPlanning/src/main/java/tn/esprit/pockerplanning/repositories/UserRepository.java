package tn.esprit.pockerplanning.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import tn.esprit.pockerplanning.entities.User;
import tn.esprit.pockerplanning.entities.enums.Role;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);

    User findByActivationToken(String token);

    Optional<User> findByPasswordResetToken(String passwordResetToken);

    @Query("{ 'role' : ?0 }")
    List<User> findByRole(Role role);

    @Query("{ 'role' : 'PROJECTMANAGER' }")
    List<User> findprojectmanager();


    @Query("{ 'role' : 'DEVELOPER' }")
    List<User> finddeveloppeur();

    @Query("{ 'role' : ?0, 'projectSet.id' : { $ne: ?1 } }")
    List<User> findByRoleAndNotAssignedToProject(Role role, String projectId);

    @Query("{ 'projectSet.id' : ?0, 'role' : ?1 }")
    List<User> findByProjectSetIdAndRole(String id, Role role);

    @Query("{ 'role' : 'DEVELOPER', 'projectSet.sprintSet.userStorySet.cardSet' : { $exists: true, $ne: [] } }")
    List<User> findDevelopersWithCards();

    @Query("{ 'role' : 'DEVELOPER', 'projectSet.sprintSet.userStorySet.noteSet' : { $exists: true, $ne: [] } }")
    List<User> findDevelopersWithNotes();

    @Query("{ 'role' : 'CHEF_DE_PROJET' }")
    User findChefsDeProjet();
}
