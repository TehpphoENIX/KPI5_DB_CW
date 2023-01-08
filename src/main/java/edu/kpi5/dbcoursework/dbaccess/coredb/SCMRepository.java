package edu.kpi5.dbcoursework.dbaccess.coredb;

import edu.kpi5.dbcoursework.entities.coredb.StudentCourseMarks;
import edu.kpi5.dbcoursework.entities.coredb.StudentCourseMarksId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SCMRepository extends CrudRepository<StudentCourseMarks, StudentCourseMarksId> {

    List<StudentCourseMarks> findByCourseId(Long courseId);

    List<StudentCourseMarks> findByStudentId(Long studentId);

    @Query(
            nativeQuery = true,
            value = """
                    SELECT *
                    FROM student_course_marks scm
                    WHERE scm.student_id = ?1 AND scm.course_id = ?2
                    """
    )
    Optional<StudentCourseMarks> findByStudentIdAndCourseId(Long studentId, Long courseId);
}
