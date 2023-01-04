package edu.kpi5.dbcoursework.userhandles;

import edu.kpi5.dbcoursework.entities.Course;
import edu.kpi5.dbcoursework.entities.Student;
import edu.kpi5.dbcoursework.entities.User;
import edu.kpi5.dbcoursework.utility.MarksList;
import edu.kpi5.dbcoursework.dbaccess.DBApi;

import java.io.File;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class StudentHandle extends Handle{
    public StudentHandle(User user) {
        super(user);
    }

    public ArrayList<AbstractMap.SimpleEntry<String, MarksList>> marksSummary(DBApi object) {
        ArrayList<AbstractMap.SimpleEntry<String, MarksList>> marks = new ArrayList<>();
        for(var course : object.getCourseList(this.getUser())){
            marks.add(new AbstractMap.SimpleEntry(course.getName(),this.getMarksOfCourse(course.getName(),object)));
        }
        return marks;
    }

    public ArrayList<Course> getMyCourses(DBApi object) {
        return new ArrayList<Course>(object.getCourseList(getUser()));
    }

    public MarksList getMarksOfCourse(String courseName, DBApi object) {
        return object.getMarksOfCourse(courseName, getLogin());
    }

    public MarksList getRSOOfCourse(String courseName, DBApi object) {
        return object.getMarksOfCourse(courseName); //трохи не розумію, що саме є рсо
                                                    //тому потім поправлю
    }

    public int CheckScholarship(DBApi object) {
        for(var student : object.getScholarshipList(false)){
            //можливо ліпше реалізувати пошук студента в DBApi, оскільки в цьому класі повинний
            //бути тільки один студент за раз
            if(student.getLogin() == this.getLogin()){
                return student.getScholarship();
                //також треба додати до класу студента стипендію з гетером і сетером
            }
        }
        return 0;
    }
}
