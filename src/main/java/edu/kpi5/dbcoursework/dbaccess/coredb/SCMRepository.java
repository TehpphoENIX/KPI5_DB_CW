package edu.kpi5.dbcoursework.dbaccess.coredb;

import edu.kpi5.dbcoursework.entities.coredb.StudentCourseMarks;
import edu.kpi5.dbcoursework.entities.coredb.StudentCourseMarksId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SCMRepository extends CrudRepository<StudentCourseMarks, StudentCourseMarksId> {
    @Query(
            nativeQuery = true,
            value = """
                    SELECT *
                    FROM student_course_marks scm
                    WHERE scm.course_id = ?0;
                    """
    )
    List<StudentCourseMarks> findByCourseId(Long courseId);
}
