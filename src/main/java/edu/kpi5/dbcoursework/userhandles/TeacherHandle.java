package edu.kpi5.dbcoursework.userhandles;

import edu.kpi5.dbcoursework.entities.Course;
import edu.kpi5.dbcoursework.entities.Student;
import edu.kpi5.dbcoursework.entities.User;
import edu.kpi5.dbcoursework.utility.MarksList;

import java.util.ArrayList;

public class TeacherHandle extends Handle {
    public TeacherHandle(User user) {
        super(user);
    }

    public int setAttestation(String courseName, MarksList marksList) {
        return 0;
    }

    public int setMark(String courseName, Student Student, int mark) {
        return 0;
    }

    public int setMarks(String courseName, MarksList marksList) {
        return 0;
    }

    public int setSocialWork(String courseName, MarksList marksList) {
        return 0;
    }

    public int setExam(String courseName, MarksList marksList) {
        return 0;
    }

    public boolean addCourse(String courseName, ArrayList<String> groups) {
        return false;
    }

    public boolean editCourse(String courseName, String newCourseName) {
        return false;
    }

    public boolean removeCourse(String courseName) {
        return false;
    }

    public Course getCourse(String courseName) {
        return null;
    }

    public ArrayList<Student> getCourseStudents(String courseName) {
        return null;
    }

    public ArrayList<Course> getCourseList() {
        return null;
    }
}
