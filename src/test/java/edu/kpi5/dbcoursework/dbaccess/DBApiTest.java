package edu.kpi5.dbcoursework.dbaccess;

import edu.kpi5.dbcoursework.configuration.RedisTestConfig;
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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.util.Pair;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = RedisTestConfig.class)
//@ActiveProfiles("test")
@EnableTransactionManagement
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class DBApiTest {
    private static redis.embedded.RedisServer redisServer;
    //MySQL
    @Autowired
    private DepartmentRepository departmentRepository;
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
    @Autowired
    MongoTemplate mongoTemplate;

    //Neo4j
    @Autowired
    AccessLevelRepository accessLevelRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    Neo4jTemplate neo4jTemplate;

    //Redis
    @Autowired
    ContributionRepository contributionRepository;
    @Autowired
    RedisServerCommands redisServerCommands;

    @Autowired
    DBApi underTest;

    @AfterEach
    public void clean(){
        scmRepository.deleteAll();
        courseRepository.deleteAll();
        studentRepository.deleteAll();
        teacherRepository.deleteAll();
        groupRepository.deleteAll();

        mongoTemplate.dropCollection(MarksList.class);

        neo4jTemplate.deleteAll(User.class);
        neo4jTemplate.deleteAll(AccessLevel.class);

        redisServerCommands.flushDb();
    }

    @Test
    void addCourse() {
        String name = "test_course";
        Long id = underTest.addCourse(name);

        var out = courseRepository.findById(id);
        assertTrue(
                out.isPresent() && out.get().getName() == name
        );
    }

    @Test
    void addStudentToCourse() {
        Student student = new Student("login","name","surname",
                null,0);
        Course course = new Course("course");
        studentRepository.save(student);
        courseRepository.save(course);

        underTest.addStudentToCourse(course.getId(), student.getId());

        var out = scmRepository.findByStudentIdAndCourseId(student.getId(), course.getId());
        assertTrue(out.isPresent());
        var out2 = marksListRepository.findById(MarksList.calcId(course.getId(), student.getId()));
        assertTrue(out2.isPresent());
    }

    @Test
    void addStudentsToCourse() {
        Group group = new Group("group",0,(short)0,null);
        ArrayList<Student> students = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Student student = new Student(String.valueOf(i),String.valueOf(i),"",group,0);
            students.add(student);
        }
        Course course = new Course("course");
        groupRepository.save(group);
        studentRepository.saveAll(students);
        courseRepository.save(course);

        underTest.addStudentsToCourse(course.getId(), group.getId());

        course = courseRepository.findById(course.getId()).get();
        assertFalse(course.getMarks().isEmpty());
        for (Student student :
                students) {
            assertTrue(course.getMarks().contains(new StudentCourseMarks(student,course)));
        }
        for (var item :
                course.getMarks()) {
            var out = marksListRepository.findById(MarksList.calcId(item.getCourse().getId(),
                    item.getStudent().getId()
            ));
            assertTrue(out.isPresent());
        }
    }

    @Test
    void addTeacherToCourse() {
        Course course = new Course("course");
        Teacher teacher = new Teacher("teacher","teacher","",null);
        courseRepository.save(course);
        teacherRepository.save(teacher);

        underTest.addTeacherToCourse(course.getId(),teacher.getId());

        teacher = teacherRepository.findById(teacher.getId()).get();
        assertTrue(teacher.getCourses().contains(course));
    }

    @Test
    void getCourseList() {
        ArrayList<AccessLevel> accessLevels = new ArrayList<>();
        accessLevels.add( new AccessLevel(AccessLevelEnum.student.label));
        accessLevels.add( new AccessLevel(AccessLevelEnum.teacher.label));
        accessLevels.add( new AccessLevel(AccessLevelEnum.admin.label));
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("student",""));
        users.add(new User("teacher",""));
        users.add(new User("admin",""));
        for (int i = 0; i < 3; i++) {
            users.get(i).getAccessLevels().add(accessLevels.get(i));
        }
        accessLevelRepository.saveAll(accessLevels);
        userRepository.saveAll(users);
        Teacher teacher = new Teacher("teacher","","",null);
        Student student = new Student("student","","",null,0);
        Course c1 = new Course("c1"), c2 = new Course("c2");
        c1.getTeachers().add(teacher);
        StudentCourseMarks scm = new StudentCourseMarks(student,c2);
        teacherRepository.save(teacher);
        studentRepository.save(student);
        courseRepository.save(c1);
        courseRepository.save(c2);
        scmRepository.save(scm);

        ArrayList<List<Course>> result = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            result.add(underTest.getCourseList(users.get(i)));
        }

        assertTrue(result.get(0).size() == 1 && result.get(0).contains(c2));
        assertTrue(result.get(1).size() == 1 && result.get(1).contains(c1));
        assertTrue(result.get(2).size() == 2 && result.get(2).contains(c1) && result.get(2).contains(c2));
    }

    @Test
    void getMarksOfCourse() {
        Course course = new Course("course");
        courseRepository.save(course);
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Student student = new Student(String.valueOf(i),String.valueOf(i),"",null,0);
            students.add(student);
        }
        studentRepository.saveAll(students);
        for (var item :
                students) {
            underTest.addStudentToCourse(course.getId(),item.getId());
        }

        List<StudentCourseMarks> list =  underTest.getMarksOfCourse(course.getId());

        for (var item :
             students) {
            assertTrue(list.contains(new StudentCourseMarks(item, course)));
        }
        for (var item :
                list) {
            assertTrue(item.getMarksList() != null);
        }
    }

    @Test
    void testGetMarksOfCourse2() {
        Course course = new Course("course");
        Student student = new Student("", "", "", null, 0);
        courseRepository.save(course);
        studentRepository.save(student);
        underTest.addStudentToCourse(course.getId(),student.getId());

        var out = underTest.getMarksOfCourse(course.getId(),student.getId());

        assertTrue(out != null);
    }

    @Test
    void getMarksOfStudent() {
        Student student = new Student("", "", "", null, 0);
        List<Course> courses = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            courses.add(new Course(String.valueOf(i)));
        }
        studentRepository.save(student);
        courseRepository.saveAll(courses);
        for (var item :
                courses) {
            underTest.addStudentToCourse(item.getId(),student.getId());
        }
        
        var out = underTest.getMarksOfStudent(student.getId());

        for (var item :
                courses) {
            assertTrue(out.contains(new StudentCourseMarks(student,item)));
        }
        for (var item :
                out) {
            assertTrue(item.getMarksList() != null);
        }
    }

    @Test
    void setMarks() {
        Course course = new Course("");
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Student student = new Student(String.valueOf(i),String.valueOf(i),"",null,0);
            students.add(student);
        }
        courseRepository.save(course);
        studentRepository.saveAll(students);
        for (var item :
                students) {
            underTest.addStudentToCourse(course.getId(),item.getId());
        }
        Map<Long,Integer> marks = new HashMap<>();
        for (int i = 0; i < 3; i++) {

        }

        //underTest.setMarks(course.getId(), "test",null);
    }//MATCH (n) OPTIONAL MATCH (n) -[r ] -() DELETE n , r ;

    @Test
    void setSocialWork() {
        Course course = new Course("");
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Student student = new Student(String.valueOf(i),String.valueOf(i),"",null,0);
            students.add(student);
        }
        courseRepository.save(course);
        studentRepository.saveAll(students);
        for (var item :
                students) {
            underTest.addStudentToCourse(course.getId(),item.getId());
        }

        underTest.setSocialWork(course.getId(), students.get(0).getId());

        var list = underTest.getMarksOfCourse(course.getId());
        for (var item :
                list) {
            if (item.getStudent().equals(students.get(0))) {
                assertTrue(item.getSocialWork());
            } else {
                assertFalse(item.getSocialWork());
            }
        }
    }

    @Test
    void addGroup() {
        Department d = new Department("", "");
        Group group = new Group("",0,(short)0,d);
        departmentRepository.save(d);
        Long id = underTest.addGroup(
                group.getName(),
                group.getYearCreated(),
                group.getSpeciality(),
                group.getDepartment().getId()
        );

        Group recieved = groupRepository.findById(id).get();
        assertEquals(group.getName(), recieved.getName());
        assertEquals(group.getYearCreated(),recieved.getYearCreated());
        assertEquals(group.getSpeciality(),recieved.getSpeciality());
        assertEquals(group.getDepartment(),recieved.getDepartment());
    }

    @Test
    void addStudentsToGroup() {
        Group group = new Group("",0,(short)0,null);
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Student student = new Student(String.valueOf(i),String.valueOf(i),"",null,0);
            students.add(student);
        }
        groupRepository.save(group);
        studentRepository.saveAll(students);

        var received = underTest.addStudentsToGroup(group.getId(),students);

        assertFalse(received);
        group = groupRepository.findById(group.getId()).get();
        assertIterableEquals(students,group.getStudents());
    }

    @Test
    void createUser(){
        ArrayList<AccessLevelEnum> enums = new ArrayList<>();
        enums.add(AccessLevelEnum.student);
        enums.add(AccessLevelEnum.teacher);
        enums.add(AccessLevelEnum.admin);
        ArrayList<AccessLevel> accessLevels = new ArrayList<>();
        for (var item :
                enums) {
            accessLevels.add(new AccessLevel(item.label));
        }
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("student",""));
        users.add(new User("teacher",""));
        users.add(new User("admin",""));
        for (int i = 0; i < 3; i++) {
            users.get(i).getAccessLevels().add(accessLevels.get(i));
        }
        accessLevelRepository.saveAll(accessLevels);

        for (int i = 0; i < users.size(); i++) {
            underTest.createUser(users.get(i).getLogin(),enums.get(i),users.get(i).getPassword());
        }

        for (User user :
                users) {
            User recieved = userRepository.findById(user.getLogin()).get();
            assertEquals(user.getAccessLevels(),recieved.getAccessLevels());
            assertEquals(user.getPassword(),recieved.getPassword());
        }
    }

    @Test
    void createTeacher() {
        AccessLevel accessLevel = new AccessLevel(AccessLevelEnum.teacher.label);
        accessLevelRepository.save(accessLevel);
        Department d = new Department("", "");
        departmentRepository.save(d);
        Teacher dummy = new Teacher("","","",d);
        String password = "";

        Long id = underTest.createTeacher(dummy.getLogin(),password,dummy.getName(),dummy.getSurname(),dummy.getDepartment().getId());

        User user = userRepository.findById(dummy.getLogin()).get();
        Teacher teacher = teacherRepository.findById(id).get();
        Contribution contribution = contributionRepository.findById(id).get();
        assertEquals(password,user.getPassword());
        assertEquals(dummy.getName(),teacher.getName());
        assertEquals(dummy.getSurname(),teacher.getSurname());
        //assertEquals(dummy.getDepartment(),teacher.getDepartment());
        assertEquals(dummy.getLogin(),teacher.getLogin());
    }

    @Test
    void createStudent(){
        AccessLevel accessLevel = new AccessLevel(AccessLevelEnum.student.label);
        accessLevelRepository.save(accessLevel);
        Group group = new Group("",0,(short)0,null);
        groupRepository.save(group);
        Student dummy = new Student("","","",group,0);
        String password = "";

        Long id = underTest.createStudent(dummy.getLogin(),password, dummy.getName(), dummy.getSurname(), dummy.getHostel(), dummy.getGroup());

        User user = userRepository.findById(dummy.getLogin()).get();
        Student student = studentRepository.findById(id).get();
        assertEquals(password,user.getPassword());
        assertEquals(dummy.getName(),student.getName());
        assertEquals(dummy.getSurname(),student.getSurname());
        assertEquals(dummy.getGroup(),student.getGroup());
        assertEquals(dummy.getLogin(),student.getLogin());
        assertEquals(dummy.getHostel(),student.getHostel());
    }


    @Test
    void loginToUser() {
        ArrayList<AccessLevelEnum> enums = new ArrayList<>();
        enums.add(AccessLevelEnum.student);
        enums.add(AccessLevelEnum.teacher);
        enums.add(AccessLevelEnum.admin);
        ArrayList<AccessLevel> accessLevels = new ArrayList<>();
        for (var item :
                enums) {
            accessLevels.add(new AccessLevel(item.label));
        }
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("student","student"));
        users.add(new User("teacher","teacher"));
        users.add(new User("admin","admin"));
        for (int i = 0; i < 3; i++) {
            users.get(i).getAccessLevels().add(accessLevels.get(i));
        }
        accessLevelRepository.saveAll(accessLevels);
        userRepository.saveAll(users);

        Pair<AccessLevelEnum,User>[][] matrix = new Pair[users.size()][users.size()];
        for (int i = 0; i < users.size(); i++) {
            for (int j = 0; j < users.size(); j++) {
                User recieved = underTest.loginToUser(users.get(i).getLogin(),users.get(j).getPassword()),
                        dummy = new User(users.get(i).getLogin(),users.get(j).getPassword());
                dummy.getAccessLevels().add(new AccessLevel(AccessLevelEnum.none.label));
                matrix[i][j]=Pair.of(
                        (i == j)?
                                enums.get(i)
                                :
                                AccessLevelEnum.none,
                        (recieved == null) ? dummy : recieved
                );
            }
        }

        for (var item:
             matrix) {
            for (Pair<AccessLevelEnum,User> pair :
                    item) {
                assertTrue(pair.getSecond().getAccessLevels().contains(new AccessLevel(pair.getFirst().label)));
            }
        }
    }

    @Test
    void applyScholarship() {
        int offset = 3;
        ArrayList<Student> students1 = new ArrayList<>(), students2 = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            students1.add(new Student(String.valueOf(i),"","",null,0));
        }
        for (int i = 0; i < 3; i++) {
            students2.add(new Student(String.valueOf(offset+i),"","",null,0));
        }
        studentRepository.saveAll(students1);
        studentRepository.saveAll(students2);

        underTest.applyScholarship(students1,false);
        underTest.applyScholarship(students2,true);

        ArrayList<Student> buffer = new ArrayList<>();
        for (var student :
                students1) {
            buffer.add(studentRepository.findById(student.getId()).get());
        }
        students1 = buffer;
        buffer = new ArrayList<>();
        for (var student :
                students2) {
            buffer.add(studentRepository.findById(student.getId()).get());
        }
        students2 = buffer;
        for (var student :
                students1) {
            assertEquals(1,student.getScholarship());
        }
        for (var student :
                students2) {
            assertEquals(2,student.getScholarship());
        }
    }
    @Test
    public void incrementContribution(){
        Contribution contribution = new Contribution(1L);
        contribution.get().put(LocalDate.of(2022,01,5),1);
        contribution.get().put(LocalDate.of(2022,01,11),2);
        contribution.get().put(LocalDate.now(),3);
        contributionRepository.save(contribution);

        underTest.incrementContribution(contribution.getId());

        Contribution received = underTest.getTeachersContribution(contribution.getId());
        for (var item :
                contribution.get().entrySet()) {
            if(item.getKey().equals(LocalDate.now())){
                assertEquals(item.getValue() + 1,received.get().get(item.getKey()));
            }else{
                assertEquals(item.getValue(),received.get().get(item.getKey()));
            }
        }
    }
}