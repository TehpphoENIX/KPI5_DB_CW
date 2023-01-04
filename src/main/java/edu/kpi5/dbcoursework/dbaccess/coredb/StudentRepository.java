package edu.kpi5.dbcoursework.dbaccess.coredb;

import edu.kpi5.dbcoursework.entities.coredb.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface StudentRepository extends CrudRepository<Student,Long> {
    //@Query()
    //List<Student> getKickList();
    //@Query()
    //List<Student> getScholarshipList();
    //@Query()
    //List<Student> getIncreasedScholarshipList();
    //ToDo
    List<Student> findAll();
}
