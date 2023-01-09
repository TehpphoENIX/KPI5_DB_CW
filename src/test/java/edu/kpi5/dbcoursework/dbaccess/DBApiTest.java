package edu.kpi5.dbcoursework.dbaccess;

import edu.kpi5.dbcoursework.dbaccess.coredb.*;
import edu.kpi5.dbcoursework.dbaccess.marksdb.MarksListRepository;
import edu.kpi5.dbcoursework.dbaccess.userdb.AccessLevelRepository;
import edu.kpi5.dbcoursework.dbaccess.userdb.UserRepository;
import edu.kpi5.dbcoursework.dbaccess.workDB.ContributionRepository;
import edu.kpi5.dbcoursework.entities.coredb.Course;
import edu.kpi5.dbcoursework.entities.coredb.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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

    //Neo4j
    @Autowired
    AccessLevelRepository accessLevelRepository;
    @Autowired
    UserRepository userRepository;

    //Redis
    @Autowired
    ContributionRepository contributionRepository;

    @Autowired
    DBApi underTest;

    @Test
    void addCourse() {
        String name = "test_course";
        underTest.addCourse(name);

        var out = courseRepository.findAll();
        boolean found = false;
        for (var item :
                out) {
            if(item.getName() == name)
                found = true;
        }
        assertTrue(found);
    }

    @Test
    void addStudentToCourse() {
        Student student = new Student("login","name","surname",
                null,0,(short)0,0.0f,false);
        Course course = new Course("course");
        studentRepository.save(student);
        courseRepository.save(course);

        underTest.addStudentToCourse(course.getId(), student.getId());

        assertTrue(
                scmRepository
                        .findByStudentIdAndCourseId(student.getId(), course.getId())
                        .isPresent()
        );
    }

    @Test
    void addStudentsToCourse() {
    }

    @Test
    void addTeacherToCourse() {
    }

    @Test
    void editCourse() {
    }

    @Test
    void removeCourse() {
    }

    @Test
    void getCourse() {
    }

    @Test
    void getCourseStudents() {
    }

    @Test
    void getCourseList() {
    }

    @Test
    void getMarksOfCourse() {
    }

    @Test
    void testGetMarksOfCourse() {
    }

    @Test
    void getMarksOfStudent() {
    }

    @Test
    void setMarks() {
    }

    @Test
    void setSocialWork() {
    }

    @Test
    void addGroup() {
    }

    @Test
    void editGroup() {
    }

    @Test
    void addStudentsToGroup() {
    }

    @Test
    void removeGroup() {
    }

    @Test
    void getGroup() {
    }

    @Test
    void getGroupList() {
    }

    @Test
    void createTeacher() {
    }

    @Test
    void createUser() {
    }

    @Test
    void removeUsers() {
    }

    @Test
    void getUser() {
    }

    @Test
    void loginToUser() {
    }

    @Test
    void getUserList() {
    }

    @Test
    void applyScholarship() {
    }

    @Test
    void getKickList() {
    }

    @Test
    void getScholarshipList() {
    }

    @Test
    void getAllStudents() {
    }

    @Test
    void getAllTeachers() {
    }
}