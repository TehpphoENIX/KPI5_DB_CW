package edu.kpi5.dbcoursework.userhandles;

import edu.kpi5.dbcoursework.entities.coredb.Course;
import edu.kpi5.dbcoursework.entities.coredb.User;
import edu.kpi5.dbcoursework.utility.MarksList;

import java.util.ArrayList;

public class StudentHandle extends Handle{
    public StudentHandle(User user) {
        super(user);
    }

    public ArrayList<Course> marksSummary() {
        return null;
    }

    public ArrayList<Course> GetMyCourses() {
        return null;
    }

    public MarksList GetMarksOfCourse(String courseName) {
        return null;
    }

    public MarksList GetRSOOfCourse(String courseName) {
        return null;
    }

    public int CheckScholarship() {
        return 0;
    }
}
