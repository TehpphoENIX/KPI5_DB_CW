package edu.kpi5.dbcoursework.dbaccess.repositories;

import edu.kpi5.dbcoursework.entities.Teacher;
import org.springframework.data.repository.CrudRepository;

public interface TeacherRepository extends CrudRepository<Teacher,Long> {
}