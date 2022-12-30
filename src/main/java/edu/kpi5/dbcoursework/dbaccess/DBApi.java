package edu.kpi5.dbcoursework.dbaccess;

import edu.kpi5.dbcoursework.dbaccess.repositories.*;
import edu.kpi5.dbcoursework.entities.*;
import edu.kpi5.dbcoursework.utility.MarksList;

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
    public boolean removeCourse(Long courseID) {
        courseRepository.deleteById(courseID);
        return false;
    }
    public List<Course> getCourse(String courseName) {
        return courseRepository.findByName(courseName);
    }
    public ArrayList<Student> getCourseStudents(String courseName) {
        return null;
    }//join
    public ArrayList<Course> getCourseList(User user) {
        return courseRepository.findAllByUser(user);
    }//join
    public MarksList getMarksOfCourse(String courseName){
        return null;
    }//MongoDB
    public MarksList getMarksOfCourse(String courseName, String studentName){
        return null;
    }//MongoDB
    public int setMarks(String courseName, MarksList marksList){
        return 0;
    }//MongoDB
    public int setExam(String courseName, MarksList marksList){
        return 0;
    }//MongoDB
    public int setSocialWork(String courseName, MarksList marksList){
        return 0;
    }//MongoDB

    public boolean addGroup(String groupName) {
        return false;
    }
    public boolean editGroup(String groupName) {
        return false;
    }
    public boolean addStudentsToGroup(String groupName, List<String> Students){
        return false;
    }
    public boolean removeGroup(Long groupID) {
        groupRepository.deleteById(groupID);
        return false;
    }
    public List<Group> getGroup(String groupName) {
        return groupRepository.findByName(groupName);
    }
    public List<Group> getGroupList() {
        return groupRepository.findAll();
    }

    public boolean createUser(String userName, AccessLevel level, String password) {
        return false;
    }//Neo4j
    public boolean editUser(String userName, AccessLevel level, String password) {
        return false;
    }//Neo4j
    public int removeUsers(List<String> userNames) {
        userRepository.deleteAllById(userNames);
        return 0;
    }
    public User getUser(String userName) {
        var out = userRepository.findById(userName);
        if(out.isPresent()){
            return out.get();
        }else{
            return null;
        }
    }
    public User loginToUser(String userName, String password) {
        return null;
    }//Neo4j method
    public List<User> getUserList() {
        return userRepository.findAll();
    }

    public int applyScholarship(List<Student> listOfStudents, boolean increased) {
        return 0;
    }
    public List<Student> getKickList() {
        return studentRepository.getKickList();
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
        return teacherRepository.findAll();
    }
}
