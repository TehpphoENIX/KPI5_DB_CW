package edu.kpi5.dbcoursework.userhandles;

public class StudentHandle extends Handle{
    public ArrayList<Course> marksSummary();
    public ArrayList<Course> GetMyCourses();
    public MarksList GetMarksOfCourse(String courseName);
    public MarksList GetRSOOfCourse(String courseName);
    public int CheckScholarship();
}
