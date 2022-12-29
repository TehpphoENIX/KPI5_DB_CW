package edu.kpi5.dbcoursework.dbaccess.repositories;

import edu.kpi5.dbcoursework.entities.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {

}
