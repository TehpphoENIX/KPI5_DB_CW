package edu.kpi5.dbcoursework.entities.coredb;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
public class StudentCourseMarksId implements Serializable {
    private Student student;
    private Course course;

    public StudentCourseMarksId() {
    }

    public StudentCourseMarksId(Student student, Course course) {
        this.student = student;
        this.course = course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentCourseMarksId that = (StudentCourseMarksId) o;

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
