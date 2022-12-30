package edu.kpi5.dbcoursework.dbaccess;

import edu.kpi5.dbcoursework.controllers.TeacherController;
import edu.kpi5.dbcoursework.dbaccess.repositories.*;
import edu.kpi5.dbcoursework.entities.*;
import edu.kpi5.dbcoursework.utility.MarksList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

//@Component
public class DBApi {

    private CourseRepository courseRepository;
    private GroupRepository groupRepository;
    private StudentRepository studentRepository;
    private TeacherRepository teacherRepository;
    private UserRepository userRepository;
    //@Autowired
    public DBApi(CourseRepository courseRepository,
                 GroupRepository groupRepository,
                 StudentRepository studentRepository,
                 TeacherRepository teacherRepository,
                 UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.groupRepository = groupRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.userRepository = userRepository;
    }

    //@Bean
    //@SessionScope
    public DBApi dbApiBean(CourseRepository courseRepository,
                           GroupRepository groupRepository,
                           StudentRepository studentRepository,
                           TeacherRepository teacherRepository,
                           UserRepository userRepository){
        return new DBApi(courseRepository,
                groupRepository,
                studentRepository,
                teacherRepository,
                userRepository);
    }

    public boolean addCourse(String courseName, ArrayList<String> groups){
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

    public MarksList getMarksOfCourse(String courseName){
        return null;
    }
    public MarksList getMarksOfCourse(String courseName, String studentName){
        return null;
    }
    public int setMarks(String courseName, MarksList marksList){
        return 0;
    }
    public int setExam(String courseName, MarksList marksList){
        return 0;
    }
    public int setSocialWork(String courseName, MarksList marksList){
        return 0;
    }

    public boolean addGroup(String groupName) {
        return false;
    }
    public boolean editGroup(String groupName) {
        return false;
    }
    public boolean addStudentsToGroup(String groupName, List<String> Students){
        return false;
    }
    public boolean removeGroup(String groupName) {
        return false;
    }
    public Group getGroup(String groupName) {
        return null;
    }
    public List<Group> getGroupList() {
        return null;
    }

    public boolean createUser(String userName, AccessLevel level, String password) {
        return false;
    }
    public boolean editUser(String userName, AccessLevel level, String password) {
        return false;
    }
    public int removeUsers(List<String> userNames) {
        return 0;
    }
    public User getUser(String userName) {
        return null;
    }
    public User loginToUser(String userName, String password) {
        return null;
    }
    public List<User> getUserList() {
        return null;
    }

    public int applyScholarship(List<Student> listOfStudents, boolean increased) {
        return 0;
    }
    public List<Student> getKickList() {
        return null;
    }
    public List<Student> getScholarshipList(boolean increased) {
        if(increased){
            return studentRepository.getIncreasedScholarshipList();
        }else
        {
            return studentRepository.getScholarshipList();
        }
    }
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public List<Teacher> getAllTeachers() {
        return null;
    }
}
