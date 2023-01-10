package edu.kpi5.dbcoursework.dbaccess;

import edu.kpi5.dbcoursework.dbaccess.coredb.*;
import edu.kpi5.dbcoursework.dbaccess.marksdb.MarksListRepository;
import edu.kpi5.dbcoursework.dbaccess.userdb.AccessLevelRepository;
import edu.kpi5.dbcoursework.dbaccess.userdb.UserRepository;
import edu.kpi5.dbcoursework.dbaccess.workDB.ContributionRepository;
import edu.kpi5.dbcoursework.entities.coredb.*;
import edu.kpi5.dbcoursework.entities.marksdb.MarksList;
import edu.kpi5.dbcoursework.entities.userdb.AccessLevel;
import edu.kpi5.dbcoursework.entities.userdb.AccessLevelEnum;
import edu.kpi5.dbcoursework.entities.userdb.User;
import edu.kpi5.dbcoursework.entities.workDB.Contribution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
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
    private SCMRepository scmRepository;

    //MongoDB
    @Autowired
    private MarksListRepository marksListRepository;

    //Neo4j
    @Autowired
    AccessLevelRepository accessLevelRepository;
    @Autowired
    UserRepository userRepository;

    //Redis
    @Autowired
    ContributionRepository contributionRepository;

//    @Bean
//    @SessionScope
//    public DBApi dbApiBean(){
//        return new DBApi();
//    }

    /**
     * add new course to the database
     * @param courseName -- new course name
     * @return course id
     */
    public long addCourse(String courseName){
        Course course = new Course(courseName);
        courseRepository.save(course);
        return course.getId();
    }

    /**
     * add 1 student to the course
     * @param courseId
     * @param studentId
     */
    public void addStudentToCourse(Long courseId, Long studentId){
        Course course = courseRepository.findById(courseId).get();
        Student student = studentRepository.findById(studentId).get();
        StudentCourseMarks studentCourseMarks = new StudentCourseMarks();
        studentCourseMarks.setCourse(course);
        studentCourseMarks.setStudent(student);
        MarksList marksList = new MarksList(MarksList.calcId(courseId,studentId));
        scmRepository.save(studentCourseMarks);
        marksListRepository.save(marksList);
    }

    /**
     * add full group to the course
     * @param courseId
     * @param groupId
     */
    public void addStudentsToCourse(Long courseId, Long groupId){
        Course course = courseRepository.findById(courseId).get();
        Group group = groupRepository.findById(groupId).get();
        ArrayList<StudentCourseMarks> listOfSCMs = new ArrayList<>();
        ArrayList<MarksList> marksLists = new ArrayList<>();
        for (Student student :
                group.getStudents()) {
            StudentCourseMarks scm = new StudentCourseMarks();
            scm.setStudent(student);
            scm.setCourse(course);
            listOfSCMs.add(scm);

            MarksList marksList = new MarksList(MarksList.calcId(courseId,student.getId()));
            marksLists.add(marksList);
        }
        scmRepository.saveAll(listOfSCMs);
        marksListRepository.saveAll(marksLists);
    }

    /**
     * add single teacher to a course
     * @param courseId
     * @param teacherId
     */
    public void addTeacherToCourse(Long courseId, Long teacherId){
        Course course = courseRepository.findById(courseId).get();
        Teacher teacher =teacherRepository.findById(teacherId).get();
        course.getTeachers().add(teacher);
        teacher.getCourses().add(course);
        courseRepository.save(course);
        teacherRepository.save(teacher);
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
        return studentRepository.findByCourse(courseId);
    }

    /**
     * get courses linked to specific user. If admin, give all courses
     * @param user -- user
     * @return list of courses
     */
    public List<Course> getCourseList(User user) {
        if(user.getAccessLevels().contains(new AccessLevel(AccessLevelEnum.admin.label))){
            return courseRepository.findAll();
        }else if(user.getAccessLevels().contains(new AccessLevel(AccessLevelEnum.teacher.label))){
            return courseRepository.findAllByTeacherLogin(user.getLogin());
        }else if(user.getAccessLevels().contains(new AccessLevel(AccessLevelEnum.student.label))){
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
    public List<StudentCourseMarks> getMarksOfCourse(Long courseId){
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
                item.setMarksList(out.get());
        }
        return markslists;
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
    public List<StudentCourseMarks> getMarksOfStudent(Long studentId){
        List<StudentCourseMarks> markslists = scmRepository.findByStudentId(studentId);
        for (var item :
                markslists) {
            var out = marksListRepository.findById(
                    MarksList.calcId(
                            item.getCourse().getId(),
                            item.getStudent().getId()
                    )
            );
            if(out.isPresent()){
                item.setMarksList(out.get());
            }
        }
        return markslists;
    }
    /**
     * insert new marks into course
     * @param courseId -- course Id
     * @param markName -- mark name
     * @param marksOfStudents -- marks of students in a map (key corresponds to student id, value is his mark)
     */
    public int setMarks(Long courseId, String markName, Map<Long, Integer> marksOfStudents){
        int succesful = 0;
        for (Map.Entry<Long,Integer> item :
                marksOfStudents.entrySet()) {
            var outMongo = marksListRepository.findById(MarksList.calcId(courseId,item.getKey()));
            var outSQL = scmRepository.findByStudentIdAndCourseId(item.getKey(),courseId);
            if(outMongo.isPresent() && outSQL.isPresent()){
                outMongo.get().getField().add(new MarksList.Mark(markName, item.getValue()));
                outSQL.get().setTotalPoints(MarksList.getTotal(outMongo.get()));
                scmRepository.save(outSQL.get());
                marksListRepository.save(outMongo.get());
                succesful++;
            }
        }
        return succesful;
    }
    //NOT REALIZED
//    /**
//     * insert new marks into courseinsert new exam marks into course@param courseId -- course Id
//     * @param courseId -- course id
//     * @param marksOfStudents -- marks of students in a map (key corresponds to student id, value is his mark)
//     */
//    public int setExam(Long courseId, Map<Long, Integer> marksOfStudents){
//        int succesful = 0;
//        for (Map.Entry<Long,Integer> item :
//                marksOfStudents.entrySet()) {
//            var outMongo = marksListRepository.findById(MarksList.calcId(courseId,item.getKey()));
//            var outSQL = scmRepository.findByStudentIdAndCourseId(item.getKey(),courseId);
//            if(outMongo.isPresent() && outSQL.isPresent()){
//                outMongo.get().setExam(item.getValue());
//                outSQL.get().setTotalPoints();
//                scmRepository.save(outSQL.get());
//                marksListRepository.save(outMongo.get());
//                succesful++;
//            }
//        }
//        return succesful;
//    }
     /**
     * insert social work into course
     * @param courseId
     * @param studentId
     */
    public int setSocialWork(Long courseId, Long studentId){
        var outSQL = scmRepository.findByStudentIdAndCourseId(studentId,courseId);
        if(outSQL.isPresent()){
            outSQL.get().setSocialWork(true);
            scmRepository.save(outSQL.get());
            return 0;
        }else {
            return 1;
        }
    }

    /**
     * Create new group
     * @param groupName
     * @param groupYear
     * @param groupSpec
     * @param department
     * @return id of new group
     */
    public Long addGroup(String groupName, Integer groupYear, Short groupSpec, Department department) {
        Group group = new Group(groupName, groupYear, groupSpec, department);
        groupRepository.save(group);
        return group.getId();
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
            //optionalGroup.get().getStudents().addAll(students);
            //groupRepository.save(optionalGroup.get());
            for (Student s:
                    students) {
                if(studentRepository.existsById(s.getId())){
                    s.setGroup(optionalGroup.get());
                }
            }
            studentRepository.saveAll(students);
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

    public Long createTeacher(String login, String password,String name, String surname, Department department){
        createUser(login,AccessLevelEnum.teacher,password);
        Teacher teacher = new Teacher(login,name,surname,department);
        teacherRepository.save(teacher);
        Contribution contribution = new Contribution(teacher.getId());
        contributionRepository.save(contribution);
        return teacher.getId();
    }

    public Long createStudent(String login, String password,String name, String surname, Integer hostel, Group group){
        createUser(login,AccessLevelEnum.student,password);
        Student student = new Student(login,name,surname,group,hostel);
        studentRepository.save(student);
        return student.getId();
    }
    /**
     * create user
     * @param userName
     * @param level
     * @param password
     * @return
     */
    public void createUser(String userName, AccessLevelEnum level, String password) {
        User user = new User(userName, password);
        var out = accessLevelRepository.findById(level.label).get();
        user.getAccessLevels().add(out);
        userRepository.save(user);
    }
    public void removeUsers(List<String> userNames) {
        userRepository.deleteAllById(userNames);
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
        var optionalUser = userRepository.findById(userName);
        if(optionalUser.isPresent() && optionalUser.get().getPassword().equals(password)){
            return optionalUser.get();
        }else{
            return null;
        }
    }
    public Student getStudent(String login){
        return studentRepository.findByLogin(login).get();
    }
    public Teacher getTeacher(String login){
        return teacherRepository.findByLogin(login).get();
    }

    public List<User> getUserList() {
        return userRepository.findAll();
    }

    public void applyScholarship(List<Student> listOfStudents, boolean increased) {
        for (Student student:
             listOfStudents) {
            if(studentRepository.existsById(student.getId())){
                student.setScholarship((increased)? 2 : 1);
            }
        }
        studentRepository.saveAll(listOfStudents);
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

    public Contribution getTeachersContribution(Long teacherId){
        var out = contributionRepository.findById(teacherId);
        if(out.isPresent()){
            return out.get();
        }else {
            return null;
        }
    }

    public void incrementContribution(Long teacherId){
        Contribution contribution = contributionRepository.findById(teacherId).get();
        Integer value = contribution.get().get(LocalDate.now());
        contribution.get().put(LocalDate.now(), value + 1);
        contributionRepository.save(contribution);
    }
}
