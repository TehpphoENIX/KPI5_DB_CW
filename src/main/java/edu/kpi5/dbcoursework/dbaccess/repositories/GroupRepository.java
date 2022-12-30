package edu.kpi5.dbcoursework.dbaccess.repositories;

import edu.kpi5.dbcoursework.entities.Course;
import edu.kpi5.dbcoursework.entities.Group;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface GroupRepository extends CrudRepository<Group, Long> {
    public List<Group> findByName(String name);
}
