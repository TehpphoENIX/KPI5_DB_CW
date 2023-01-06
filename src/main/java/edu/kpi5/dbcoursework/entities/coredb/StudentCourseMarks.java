package edu.kpi5.dbcoursework.entities.coredb;

import jakarta.persistence.*;
import org.springframework.data.mongodb.repository.CountQuery;

@Entity
@Table(name = "student_course_marks")
@IdClass(StudentCourseMarksId.class)
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

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Integer getSocialWork() {
        return socialWork;
    }

    public void setSocialWork(Integer socialWork) {
        this.socialWork = socialWork;
    }
}
