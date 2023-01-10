package edu.kpi5.dbcoursework.userhandles;


import edu.kpi5.dbcoursework.entities.coredb.*;
import edu.kpi5.dbcoursework.entities.marksdb.MarksList;
import edu.kpi5.dbcoursework.dbaccess.DBApi;
import edu.kpi5.dbcoursework.entities.userdb.User;
import org.springframework.web.servlet.support.JstlUtils;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class StudentHandle extends Handle{
    private Student student;
    public StudentHandle(User user, DBApi object) {
        super(user);
        student = object.getStudent(user.getLogin());
    }

    public final Student getStudent(){
        return student;
    }

    public List<StudentCourseMarks> marksSummary(DBApi object) {
        return object.getMarksOfStudent(student.getId());
    }

    public List<Course> getMyCourses(DBApi object) {
        return object.getCourseList(this.getUser());
    }

    public MarksList getMarksOfCourse(Long courseId, DBApi object) {
        return object.getMarksOfCourse(courseId, student.getId());
    }

    // THIS FUNCTION IS NOT REALISED

//    public MarksList getRSOOfCourse(String courseName, DBApi object) {
//        return null; //трохи не розумію, що саме є рсо
//                                                    //тому потім поправлю
//    }

//    public int CheckScholarship(DBApi object) {
//        return student.getScholarship();
//    }
    public Student get(){
        return student;
    }
}
