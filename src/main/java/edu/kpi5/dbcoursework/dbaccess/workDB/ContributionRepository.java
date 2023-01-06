package edu.kpi5.dbcoursework.dbaccess.workDB;

import edu.kpi5.dbcoursework.entities.workDB.RedisEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContributionRepository extends CrudRepository<RedisEntity, Long> {

}
