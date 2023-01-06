package edu.kpi5.dbcoursework.entities.coredb;

import jakarta.persistence.*;
import org.springframework.data.mongodb.repository.CountQuery;

@Entity
@Table(name = "student_course_marks")
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
}
