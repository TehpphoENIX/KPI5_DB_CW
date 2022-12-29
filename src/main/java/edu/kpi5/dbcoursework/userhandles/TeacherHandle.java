package edu.kpi5.dbcoursework.userhandles;

import java.util.ArrayList;

public class TeacherHandle extends Handle {
    public int setAttestation(String courseName, MarksList marksList);
    public int setMark(String courseName, Student Student, int mark);
    public int setMarks(String courseName, MarksList marksList);
    public int setSocialWork(String courseName, MarksList marksList);
    public int setExam(String courseName, MarksList marksList);
    public boolean addCourse(String courseName, ArrayList<String> groups);
    public boolean editCourse(String courseName, String newCourseName);
    public boolean removeCourse(String courseName);
    public Course getCourse(String courseName);
    public ArrayList<Student> getCourseStudents(String courseName);
    public ArrayList<Course> getCourseList();
}
