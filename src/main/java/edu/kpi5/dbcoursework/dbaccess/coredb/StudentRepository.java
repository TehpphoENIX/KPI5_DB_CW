package edu.kpi5.dbcoursework.dbaccess.coredb;

import edu.kpi5.dbcoursework.entities.coredb.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@Repository
public interface StudentRepository extends CrudRepository<Student,Long> {
    //wrong query
    @Query(
            value = """
                    SELECT s.*
                    FROM STUDENT s, (
                       SELECT s.id AS i, COUNT(m.total_points) AS c
                    	FROM STUDENT s
                       	JOIN student_course_marks m ON m.student_id = s.id
                       	WHERE m.total_points <= 2
                       	GROUP BY s.id) all_bad_marks
                       WHERE all_bad_marks.c > 2 AND all_bad_marks.i = s.id;""",
            nativeQuery = true)
    List<Student> getKickList();
    @Query(
            value = """
                    SELECT s.*
                    FROM STUDENT s
                        LEFT JOIN(
                        SELECT s.id AS i, COUNT(m.total_points) AS c
                        FROM STUDENT s
                            JOIN student_course_marks m ON m.student_id = s.id
                            GROUP BY s.id) all_marks
                        ON all_marks.i = s.id
                        LEFT JOIN (
                            SELECT s.id AS i, COUNT(m.total_points) AS c
                            FROM STUDENT s
                            JOIN student_course_marks m ON m.student_id = s.id
                            WHERE m.total_points = 5 OR m.total_points = 4
                            GROUP BY s.id) all_good_marks
                        ON  all_good_marks.i = s.id
                        LEFT JOIN (
                            SELECT s.id AS i, COUNT(m.total_points) AS c
                            FROM STUDENT s
                            JOIN student_course_marks m ON m.student_id = s.id
                            WHERE m.total_points = 3
                            GROUP BY s.id) all_marks3
                        ON  all_marks3.i = s.id
                        LEFT JOIN (
                            SELECT s.id AS i, COUNT(m.total_points) AS c
                            FROM STUDENT s
                            JOIN student_course_marks m ON m.student_id = s.id
                            WHERE m.total_points = 5
                            GROUP BY s.id) all_marks5
                        ON  all_marks5.i = s.id
                       LEFT JOIN (
                            SELECT s.id AS i, COUNT(CASE WHEN m.social_work THEN 1 END) AS c
                            FROM STUDENT s
                            JOIN student_course_marks m ON m.student_id = s.id
                            GROUP BY s.id) sw
                        ON  sw.i = s.id
                        WHERE (all_marks.c = all_good_marks.c AND all_marks5.c != all_good_marks.c) OR
                        (
                        all_marks.c = (all_good_marks.c + 1) AND
                        all_marks3.c = 1 AND sw.c >=1);""",
            nativeQuery = true)
    List<Student> getScholarshipList();
    @Query(
            value = """
                    SELECT s.*
                    	FROM STUDENT s, (
                        SELECT s.id AS i, COUNT(m.total_points) AS c
                    		FROM STUDENT s
                        	JOIN student_course_marks m ON m.student_id = s.id
                        	GROUP BY s.id) all_marks,
                        (
                        SELECT s.id AS i, COUNT(m.total_points) AS c
                    		FROM STUDENT s
                        	JOIN student_course_marks m ON m.student_id = s.id
                        	WHERE m.total_points = 5
                        	GROUP BY s.id) all_good_marks
                        WHERE all_marks.c = all_good_marks.c AND all_marks.i = s.id AND all_marks.i = all_good_marks.i;""",
            nativeQuery = true)
    List<Student> getIncreasedScholarshipList();
    List<Student> findAll();

    Optional<Student> findByLogin(String login);

    @Query(
            nativeQuery = true,
            value = "SELECT s.* " +
                    "FROM course as c " +
                    "JOIN student_course_marks scm ON scm.course_id = c.id " +
                    "JOIN student s ON s.id = scm.student_id " +
                    "WHERE c.id = :courseId"
    )
    public List<Student> findByCourse(@Param("courseId") Long courseId);
}
