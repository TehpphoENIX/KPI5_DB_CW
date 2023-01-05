package edu.kpi5.dbcoursework.entities.coredb;

import jakarta.persistence.*;

@Entity
@Table(name = "STUDENT_COURSE_MARKS")
public class StudentCourseMarks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name="course_id")
    private Course course;
}
