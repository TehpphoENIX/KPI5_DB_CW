package edu.kpi5.dbcoursework.dbaccess.repositories;

import edu.kpi5.dbcoursework.entities.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student,Long> {
}
