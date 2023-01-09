package edu.kpi5.dbcoursework.userhandles;


import edu.kpi5.dbcoursework.entities.coredb.*;
import edu.kpi5.dbcoursework.entities.marksdb.MarksList;
import edu.kpi5.dbcoursework.dbaccess.DBApi;
import edu.kpi5.dbcoursework.entities.userdb.User;

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

    public int CheckScholarship(DBApi object) {
//        for(var student : object.getScholarshipList(false)){
//            //можливо ліпше реалізувати пошук студента в DBApi, оскільки в цьому класі повинний
//            //бути тільки один студент за раз
//            if(student.getLogin() == this.getLogin()){
//                return student.getScholarship();
//                //також треба додати до класу студента стипендію з гетером і сетером
//            }
//        }
        return 0;
    }//todo
}
