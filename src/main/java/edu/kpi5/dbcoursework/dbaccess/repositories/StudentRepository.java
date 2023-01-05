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
            value = "SELECT s.name, all_good_marks.c, all_marks.c\n" +
                    "FROM students s, (\n" +
                    "    SELECT s.s_id AS i, COUNT(m.mark) AS c\n" +
                    "FROM students s\n" +
                    "    JOIN marks m ON m.s_id = s.s_id\n" +
                    "    GROUP BY s.s_id) all_marks,\n" +
                    "    (\n" +
                    "    SELECT s.s_id AS i, COUNT(m.mark) AS c\n" +
                    "FROM students s\n" +
                    "    JOIN marks m ON m.s_id = s.s_id\n" +
                    "    WHERE m.mark = 5\n" +
                    "    GROUP BY s.s_id) all_good_marks\n" +
                    "    WHERE all_marks.c = all_good_marks.c AND all_marks.i = s.s_id AND all_marks.i = all_good_marks.i;",
            nativeQuery = true)
    List<Student> getIncreasedScholarshipList();
    List<Student> findAll();
}
