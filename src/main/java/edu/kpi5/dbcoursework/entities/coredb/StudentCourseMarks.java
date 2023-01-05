package edu.kpi5.dbcoursework.entities.coredb;

import jakarta.persistence.*;

@Entity
@Table(name = "STUDENT_COURSE_MARKS")
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
}
