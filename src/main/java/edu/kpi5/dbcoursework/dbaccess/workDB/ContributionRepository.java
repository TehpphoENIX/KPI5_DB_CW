package edu.kpi5.dbcoursework.dbaccess.workDB;

import edu.kpi5.dbcoursework.entities.workDB.Contribution;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContributionRepository extends CrudRepository<Contribution, Long> {

}
