package edu.kpi5.dbcoursework.dbaccess;

import edu.kpi5.dbcoursework.dbaccess.coredb.*;
import edu.kpi5.dbcoursework.dbaccess.marksdb.MarksListRepository;
import edu.kpi5.dbcoursework.dbaccess.userdb.UserRepository;
import edu.kpi5.dbcoursework.entities.coredb.*;
import edu.kpi5.dbcoursework.entities.marksdb.MarksList;
import edu.kpi5.dbcoursework.entities.userdb.AccessLevel;
import edu.kpi5.dbcoursework.entities.userdb.AccessLevelEnum;
import edu.kpi5.dbcoursework.entities.userdb.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Component
public class DBApi {
    //MySQL
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SCMRepository scmRepository;
    //MongoDB
    @Autowired
    private MarksListRepository marksListRepository;
    //@Autowired
    @Bean
    @SessionScope
    public DBApi dbApiBean(){
        return new DBApi();
    }

    /**
     * add new course to the database
     * @param courseName -- new course name
     */
    public void addCourse(String courseName){
        courseRepository.save(new Course(courseName));
    }

    /**
     * update course in the database
     * @param course -- course update
     */
    public void editCourse(Course course) {
        courseRepository.save(course);
    }

    /**
     * delete course from database
     * @param courseID -- course id
     */
    public void removeCourse(Long courseID) {
        courseRepository.deleteById(courseID);
    }

    /**
     * get course from database
     * @param courseId -- course id
     * @return course
     */
    public Course getCourse(Long courseId) {
        var out = courseRepository.findById(courseId);
        if(out.isPresent()){
            return out.get();
        }else{
            return null;
        }
    }

    /**
     * get course of the database
     * @param courseId -- course to search in
     * @return list of students
     */
    public List<Student> getCourseStudents(Long courseId) {
        return courseRepository.getStudentsByCourse(courseId);
    }

    /**
     * get courses linked to specific user. If admin, give all courses
     * @param user -- user
     * @return list of courses
     */
    public List<Course> getCourseList(User user) {
        if(user.getAccessLevel().contains(new AccessLevel(AccessLevelEnum.admin.label))){
            return courseRepository.findAll();
        }else if(user.getAccessLevel().contains(new AccessLevel(AccessLevelEnum.teacher.label))){
            return courseRepository.findAllByTeacherLogin(user.getLogin());
        }else if(user.getAccessLevel().contains(new AccessLevel(AccessLevelEnum.student.label))){
            return courseRepository.findAllByStudentLogin(user.getLogin());
        }
        else{
            return null;
        }
    }

    /**
     * Get all marks of a specific course
     * @param courseId -- course id
     * @return list of marks
     */
    public List<MarksList> getMarksOfCourse(Long courseId){
        List<StudentCourseMarks> markslists = scmRepository.findByCourseId(courseId);
        ArrayList<MarksList> matrix = new ArrayList<>();
        for (var item :
                markslists) {
            var out = marksListRepository.findById(
                    MarksList.calcId(
                            item.getCourse().getId(),
                            item.getStudent().getId()
                    )
            );
            if(out.isPresent())
                matrix.add(out.get());
        }
        return matrix;
    }

    /**
     * Get marks of a specific student
     * @param courseId -- course id
     * @param studentId -- student id
     * @return marks of that student in that course
     */
    public MarksList getMarksOfCourse(Long courseId, Long studentId){
        var out = marksListRepository.findById(MarksList.calcId(courseId,studentId));
        if(out.isPresent()){
            return out.get();
        }else {
            return null;
        }
    }

    /**
     * Get all marks of a specific student
     * @param studentId -- student id
     * @return list of marks of that student
     */
    public List<MarksList> getMarksOfStudent(Long studentId){
        List<StudentCourseMarks> markslists = scmRepository.findByStudentId(studentId);
        ArrayList<MarksList> matrix = new ArrayList<>();
        for (var item :
                markslists) {
            var out = marksListRepository.findById(
                    MarksList.calcId(
                            item.getCourse().getId(),
                            item.getStudent().getId()
                    )
            );
            if(out.isPresent())
                matrix.add(out.get());
        }
        return matrix;
    }
    /**
     * insert new marks into course
     * @param courseName -- todo change to id
     * @param marksList -- todo change
     */
    public int setMarks(Long courseId, MarksList marksList){
        return 0;
    }//Mongodb ToDo
     /**
     * insert new exam marks into course
     * @param courseName -- todo change to id
     * @param marksList -- todo change
     */
    public int setExam(Long courseId, MarksList marksList){
        return 0;
    }//Mongodb ToDo
     /**
     * insert social work into course
     * @param courseName -- todo change to id
     * @param marksList -- todo change
     */
    public int setSocialWork(Long courseId, Long studentId){

        return 0;
    }//Mongodb ToDo

    /**
     * Create new group
     * @param groupName
     * @param groupYear
     * @param groupSpec
     * @param department
     */
    public void addGroup(String groupName, Integer groupYear, Short groupSpec, Department department) {
        Group group = new Group(groupName, groupYear, groupSpec, department);
        groupRepository.save(group);
    }

    /**
     * update group
     * @param group
     */
    public void editGroup(Group group) {
        groupRepository.save(group);
    }

    /**
     * add students to group
     * @param groupId
     * @param students
     * @return
     */
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

    /**
     * remove group
     * @param groupID
     */
    public void removeGroup(Long groupID) {
        groupRepository.deleteById(groupID);
    }

    /**
     * get group
     * @param groupID
     * @return
     */
    public Group getGroup(Long groupID) {
        var out = groupRepository.findById(groupID);
        if(out.isPresent()){
            return out.get();
        }else{
            return null;
        }
    }

    /**
     * get list of all groups
     * @return
     */
    public List<Group> getGroupList() {
        return groupRepository.findAll();
    }

    /**
     * create user
     * @param userName
     * @param level
     * @param password
     * @return
     */
    public boolean createUser(String userName, AccessLevelEnum level, String password) {
        return false;
    }//Neo4j todo
    public boolean editUser(String userName, AccessLevelEnum level, String password) {
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

    public void applyScholarship(List<Student> listOfStudents, boolean increased) {
        for (Student student:
             listOfStudents) {
            student.setScholarship((increased)? 2 : 1);
        }
        studentRepository.saveAll(listOfStudents);
    }
    public List<Student> getKickList() {
        return studentRepository.getKickList();
    }//todo
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
