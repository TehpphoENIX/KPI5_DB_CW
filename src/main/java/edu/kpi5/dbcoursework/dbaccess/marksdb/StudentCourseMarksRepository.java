package edu.kpi5.dbcoursework.dbaccess.marksdb;

import edu.kpi5.dbcoursework.entities.marksdb.StudentCourseMarks;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudentCourseMarksRepository extends MongoRepository<StudentCourseMarks, String> {
}
