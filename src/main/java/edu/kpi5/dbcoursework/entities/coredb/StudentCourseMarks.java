package edu.kpi5.dbcoursework.entities.coredb;

import edu.kpi5.dbcoursework.entities.marksdb.MarksList;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.repository.CountQuery;

@Entity
@Table(name = "student_course_marks")
@IdClass(StudentCourseMarksId.class)
@Getter
@Setter
public class StudentCourseMarks {

    @Id
    @ManyToOne
    @JoinColumn(name="student_id")
    private Student student;

    @Id
    @ManyToOne
    @JoinColumn(name="course_id")
    private Course course;

    @Column(name = "total_points")
    private Integer totalPoints;

    @Column(name = "social_work")
    private Integer socialWork;

    @Transient
    private MarksList marksList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentCourseMarks that = (StudentCourseMarks) o;

        if (!student.equals(that.student)) return false;
        return course.equals(that.course);
    }

    @Override
    public int hashCode() {
        int result = student.hashCode();
        result = 31 * result + course.hashCode();
        return result;
    }
}
