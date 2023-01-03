package edu.kpi5.dbcoursework.dbaccess.repositories;

import edu.kpi5.dbcoursework.entities.Course;
import edu.kpi5.dbcoursework.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {
    public List<Course> findByName(String name);

    //public List<Course> findAllByUser(User user);
}
