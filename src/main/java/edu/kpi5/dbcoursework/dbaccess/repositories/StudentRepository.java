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
            value = """
                    SELECT s.*
                    FROM students s
                        LEFT JOIN(
                        SELECT s.s_id AS i, COUNT(m.mark) AS c
                        FROM students s
                            JOIN marks m ON m.s_id = s.s_id
                            GROUP BY s.s_id) all_marks
                        ON all_marks.i = s.s_id
                          
                        LEFT JOIN (
                            SELECT s.s_id AS i, COUNT(m.mark) AS c
                            FROM students s
                            JOIN marks m ON m.s_id = s.s_id
                            WHERE m.mark = 5 OR m.mark = 4
                            GROUP BY s.s_id) all_good_marks
                        ON  all_good_marks.i = s.s_id
                       
                        LEFT JOIN (
                            SELECT s.s_id AS i, COUNT(m.mark) AS c
                            FROM students s
                            JOIN marks m ON m.s_id = s.s_id
                            WHERE m.mark = 3
                            GROUP BY s.s_id) all_marks3
                        ON  all_marks3.i = s.s_id
                       
                        WHERE all_marks.c = all_good_marks.c OR
                        (
                        all_marks.c = (all_good_marks.c + 1) AND
                        all_marks3.c = 1);""",
            nativeQuery = true)
    List<Student> getScholarshipList();
    @Query(
            value = """
                    SELECT s.*
                    FROM students s, (
                        SELECT s.s_id AS i, COUNT(m.mark) AS c
                    FROM students s
                        JOIN marks m ON m.s_id = s.s_id
                        GROUP BY s.s_id) all_marks,
                        (
                        SELECT s.s_id AS i, COUNT(m.mark) AS c
                    FROM students s
                        JOIN marks m ON m.s_id = s.s_id
                        WHERE m.mark = 5
                        GROUP BY s.s_id) all_good_marks
                        WHERE all_marks.c = all_good_marks.c AND all_marks.i = s.s_id AND all_marks.i = all_good_marks.i;""",
            nativeQuery = true)
    List<Student> getIncreasedScholarshipList();
    List<Student> findAll();
}
