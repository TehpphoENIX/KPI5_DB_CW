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
import edu.kpi5.dbcoursework.userhandles.TeacherHandle;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@EnableTransactionManagement
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class DBApiTest {


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
                null,0,(short)0,0.0f,false);
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
            Student student = new Student(String.valueOf(i),String.valueOf(i),"",group,0,(short)0,0.0F,false);
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

        course = courseRepository.findById(course.getId()).get();
        assertTrue(course.getTeachers().contains(teacher));
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
        Student student = new Student("student","","",null,0,(short)0,0.0F,false);
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
            Student student = new Student(String.valueOf(i),String.valueOf(i),"",null,0,(short)0,0.0f,false);
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
        Student student = new Student("", "", "", null, 0, (short) 0, 0.F, false);
        courseRepository.save(course);
        studentRepository.save(student);
        underTest.addStudentToCourse(course.getId(),student.getId());

        var out = underTest.getMarksOfCourse(course.getId(),student.getId());

        assertTrue(out != null);
    }

    @Test
    void getMarksOfStudent() {
        Student student = new Student("", "", "", null, 0, (short) 0, 0.F, false);
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
            Student student = new Student(String.valueOf(i),String.valueOf(i),"",null,0,(short)0,0.0f,false);
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
    }

    @Test
    void addGroup() {
    }

    @Test
    void addStudentsToGroup() {
    }

    @Test
    void createTeacher() {
    }

    @Test
    void loginToUser() {
    }

    @Test
    void applyScholarship() {
    }
}