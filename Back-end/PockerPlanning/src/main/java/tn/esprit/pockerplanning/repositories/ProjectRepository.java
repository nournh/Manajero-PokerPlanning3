package tn.esprit.pockerplanning.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import tn.esprit.pockerplanning.entities.Project;
import tn.esprit.pockerplanning.entities.enums.Domain;
import tn.esprit.pockerplanning.entities.enums.StatusProject;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface ProjectRepository extends MongoRepository<Project, String> {
    Set<Project> findByUserSetId(long userSet_id);
    int countProjectBystatusProject(StatusProject status);
    List<Project> findByBadgetAndStartDateGreaterThanEqualAndEndDateLessThanEqualAndStatusProjectAndDomainAndNameAndNbDeveloper(
            Integer badget,
            LocalDate startDate,
            LocalDate endDate,
            StatusProject status,
            Domain domain,
            String name,
            Integer nbDeveloper
    );

    List<Project> findByUserSetIdAndBadgetEquals(long id, int badget);
}