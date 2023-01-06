package edu.kpi5.dbcoursework.entities.coredb;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;

public class StudentCourseMarksId implements Serializable {
    private Student student;
    private Course course;

    public StudentCourseMarksId() {
    }

    public StudentCourseMarksId(Student student, Course course) {
        this.student = student;
        this.course = course;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
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
