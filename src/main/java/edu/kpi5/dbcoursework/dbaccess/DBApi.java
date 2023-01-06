package edu.kpi5.dbcoursework.dbaccess;

import edu.kpi5.dbcoursework.dbaccess.coredb.*;
import edu.kpi5.dbcoursework.entities.coredb.*;
import edu.kpi5.dbcoursework.utility.MarksList;
import org.springframework.context.annotation.Bean;
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

    @Bean
    @SessionScope
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


    public void addCourse(String courseName){
        courseRepository.save(new Course(courseName));
    }
    public void editCourse(Course course) {
        courseRepository.save(course);
    }
    public void removeCourse(Long courseID) {
        courseRepository.deleteById(courseID);
    }
    public Course getCourse(Long courseId) {
        var out = courseRepository.findById(courseId);
        if(out.isPresent()){
            return out.get();
        }else{
            return null;
        }
    }
    public List<Student> getCourseStudents(Course course) {
        return courseRepository.getStudentsByCourse(course.getId());
    }
    public List<Course> getCourseList(User user) {
        switch (user.getAccessLevel()){
            case student -> {
                return courseRepository.findAllByStudentLogin(user.getLogin());
            }
            case teacher -> {
                return courseRepository.findAllByTeacherLogin(user.getLogin());
            }
            case admin -> {
                return courseRepository.findAll();
            }
            default -> {
                return null;
            }
        }
    }
    public MarksList getMarksOfCourse(Long courseId){
        return null;
    }//Mongodb ToDo
    public MarksList getMarksOfCourse(Long courseId, Long studentId){
        return null;
    }//Mongodb ToDo
    public int setMarks(String courseName, MarksList marksList){
        return 0;
    }//Mongodb ToDo
    public int setExam(String courseName, MarksList marksList){
        return 0;
    }//Mongodb ToDo
    public int setSocialWork(String courseName, MarksList marksList){
        return 0;
    }//Mongodb ToDo
    public void addGroup(String groupName, Integer groupYear, Short groupSpec, Department department) {
        Group group = new Group(groupName, groupYear, groupSpec, department);
        groupRepository.save(group);
    }
    public void editGroup(Group group) {
        groupRepository.save(group);
    }
    public boolean addStudentsToGroup(Long groupId, List<Student> students){
        var optionalGroup = groupRepository.findById(groupId);
        if(optionalGroup.isPresent()){
            optionalGroup.get().getStudents().addAll(students);
            groupRepository.save(optionalGroup.get());
            return false;
        }else{
            return true;
        }
    }
    public void removeGroup(Long groupID) {
        groupRepository.deleteById(groupID);
    }
    public Group getGroup(Long groupID) {
        var out = groupRepository.findById(groupID);
        if(out.isPresent()){
            return out.get();
        }else{
            return null;
        }
    }
    public List<Group> getGroupList() {
        return groupRepository.findAll();
    }

    public boolean createUser(String userName, AccessLevel level, String password) {
        return false;
    }//Neo4j todo
    public boolean editUser(String userName, AccessLevel level, String password) {
        return false;
    }//Neo4j todo
    public void removeUsers(List<String> userNames) {
        userRepository.deleteAllById(userNames);
    }//Neo4j todo
    public User getUser(String userName) {
        var out = userRepository.findById(userName);
        if(out.isPresent()){
            return out.get();
        }else{
            return null;
        }
    }//Neo4j todo
    public User loginToUser(String userName, String password) {
        return null;
    }//Neo4j todo
    public List<User> getUserList() {
        return userRepository.findAll();
    }//Neo4j todo

    public int applyScholarship(List<Student> listOfStudents, boolean increased) {
        return 0;
    }//todo
    public List<Student> getKickList() {
        //return studentRepository.getKickList();
        return null;
    }//todo
    public List<Student> getScholarshipList(boolean increased) {
        if(increased){
            //return studentRepository.getIncreasedScholarshipList();
            return null;
        }else
        {
            //return studentRepository.getScholarshipList();
            return null;
        }
    }//todo
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }
}
