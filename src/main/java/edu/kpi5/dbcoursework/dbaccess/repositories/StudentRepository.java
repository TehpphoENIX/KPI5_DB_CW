package edu.kpi5.dbcoursework.dbaccess.repositories;

import edu.kpi5.dbcoursework.entities.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface StudentRepository extends CrudRepository<Student,Long> {
    //wrong query
    @Query(
            value = "" +
                    "SELECT * " +
                    "FROM USERS u",
            nativeQuery = true)
    List<Student> getKickList();
    //wrong query
    @Query(
            value = "SELECT * FROM USERS u",
            nativeQuery = true)
    List<Student> getScholarshipList();
    @Query(
            value = "SELECT * FROM USERS u",
            nativeQuery = true)
    List<Student> getIncreasedScholarshipList();
    List<Student> findAll();
}
