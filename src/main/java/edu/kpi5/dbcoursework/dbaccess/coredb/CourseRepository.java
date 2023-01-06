package edu.kpi5.dbcoursework.dbaccess.coredb;

import edu.kpi5.dbcoursework.entities.coredb.Course;
import edu.kpi5.dbcoursework.entities.coredb.Student;
import edu.kpi5.dbcoursework.entities.coredb.User;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {
    public List<Course> findByName(String name);
    @Query(//ToDo
            nativeQuery = true,
            value = "SELECT * FROM COURSE as C" +
                    "WHERE EXISTS(" +
                    "   SELECT NULL" +
                    "       FROM" +
                    "           STUDENT AS S" +
                    "       JOIN USER AS U" +
                    "           ON S.user = U.login" +
                    "       WHERE" +
                    ")"
    )
    public List<Course> findAllByUser(User user);
    @Query(
            nativeQuery = true,
            value = "SELECT *" +
                    "FROM course as c" +
                    "JOIN student_course_marks scm ON scm.course_id = c.id" +
                    "JOIN student s ON s.id = scm.student_id" +
                    "WHERE c.id=:courseId"

    )
    public List<Student> getStudentsByCourse(@Param("courseId") Long courseId);
}
//SELECT
//        NULL
//    FROM
//        TABLE2 AS t2
//        JOIN TABLE4 AS t4
//            ON t2.t4_ref = t4.id
//    WHERE
//        t1.id = t2.t1_ref
//        AND t4.type = 'something'
//    UNION ALL
//    SELECT
//        NULL
//    FROM
//        TABLE3 AS t3
//        JOIN TABLE4 AS t4
//            ON t3.t4_ref = t4.id
//    WHERE
//        t1.id = t3.t1_ref
//        AND t4.type = 'something'