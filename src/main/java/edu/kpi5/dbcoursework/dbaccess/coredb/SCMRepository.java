package edu.kpi5.dbcoursework.dbaccess.coredb;

import edu.kpi5.dbcoursework.entities.coredb.StudentCourseMarks;
import edu.kpi5.dbcoursework.entities.coredb.StudentCourseMarksId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

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

    @Modifying
    @Transactional
    @Query(
            nativeQuery = true,
            value = """
                    DELETE
                    FROM student_course_marks scm
                    WHERE scm.course_id = ?1
                    """
    )
    void deleteByCourseId(Long courseID);
    @Modifying
    @Transactional
    @Query(
            nativeQuery = true,
            value = """
                    DELETE
                    FROM student_course_marks scm
                    WHERE scm.student_id = ?1
                    """
    )
    void deleteByStudentId(Long studentId);
}
