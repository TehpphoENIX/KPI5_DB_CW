package edu.kpi5.dbcoursework.dbaccess.coredb;

import edu.kpi5.dbcoursework.entities.coredb.Group;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface GroupRepository extends CrudRepository<Group, Long> {
    public List<Group> findByName(String name);
    List<Group> findAll();
}
