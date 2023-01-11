package edu.kpi5.dbcoursework.dbaccess.coredb;

import edu.kpi5.dbcoursework.entities.coredb.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CourseRepositoryTest {

    @Autowired
    private CourseRepository underTest;
    @Autowired
    private StudentRepository studentRep;
    @Autowired
    private TeacherRepository teacherRep;
    @Autowired
    private SCMRepository SCMRepo;

    @AfterEach
    public void clean() {
        SCMRepo.deleteAll();
        underTest.deleteAll();
        studentRep.deleteAll();
        teacherRep.deleteAll();
    }

    @Test
    void findAll() {
        ArrayList<Course> courses = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            courses.add(new Course(String.valueOf(i)));
        }
        underTest.saveAll(courses);

        var result = underTest.findAll();

        assertIterableEquals(courses,result);
    }

    @Test
    void delete() {
        ArrayList<Course> courses = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            courses.add(new Course(String.valueOf(i)));
        }
        underTest.saveAll(courses);

        for (long i = 1; i < 11; i++) {
            underTest.deleteById(i);
        }

        assertEquals(0,underTest.count());
    }

    @Test
    void findById() {

        Course course = new Course("course1");

        underTest.save(course);

        Optional<Course> result = underTest.findById(course.getId());

        result.ifPresent(value -> assertEquals(value.getId(), course.getId()));
    }

    @Test
    void findAllByStudentLogin() {

        Student student = new Student("login", "name", "surname", null, 0);

        studentRep.save(student);

        List<Course> courses = new ArrayList<>();
        courses.add(new Course("course1"));
        courses.add(new Course("course2"));

        underTest.saveAll(courses);

        List<StudentCourseMarks> studMarks = new ArrayList<>();
        studMarks.add( new StudentCourseMarks(student, courses.get(0)));
        studMarks.add( new StudentCourseMarks(student, courses.get(1)));

        SCMRepo.saveAll(studMarks);

        var result = underTest.findAllByStudentLogin(student.getLogin());

        assertIterableEquals(courses,result);
    }

    @Test
    void findAllByTeacherLogin() {
        List<Course> courses = new ArrayList<>();
        courses.add(new Course("course1"));
        courses.add(new Course("course2"));
        Teacher teacher = new Teacher("login", "name", "surname", null);
        Set<Course> temp = new HashSet<>(courses);
        teacher.setCourses(temp);
        for (var item:
             courses) {
            item.getTeachers().add(teacher);
        }
        teacherRep.save(teacher);
        underTest.saveAll(courses);


        var result = underTest.findAllByTeacherLogin(teacher.getLogin());

        assertIterableEquals(courses,result);
    }
}