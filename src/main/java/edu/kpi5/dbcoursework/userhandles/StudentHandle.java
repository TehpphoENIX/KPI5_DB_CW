package edu.kpi5.dbcoursework.userhandles;

import edu.kpi5.dbcoursework.entities.Course;
import edu.kpi5.dbcoursework.entities.User;
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

    public MarksList GetMarksOfCourse(Long courseId) {
        return null;
    }

    public MarksList GetRSOOfCourse(Long courseId) {
        return null;
    }

    public int CheckScholarship() {
        return 0;
    }
}
