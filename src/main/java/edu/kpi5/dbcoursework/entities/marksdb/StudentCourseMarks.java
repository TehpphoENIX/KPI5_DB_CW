package edu.kpi5.dbcoursework.entities.marksdb;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("student_course_marks")
public class StudentCourseMarks {
    @Id
    private String id;

    public StudentCourseMarks(String id) {
        this.id = id;
    }
}
