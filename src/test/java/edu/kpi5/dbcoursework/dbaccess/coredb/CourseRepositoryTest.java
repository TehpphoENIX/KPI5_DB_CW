package edu.kpi5.dbcoursework.dbaccess.coredb;

import edu.kpi5.dbcoursework.entities.coredb.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CourseRepositoryTest {

    @Autowired
    private CourseRepository underTest;
    @Autowired
    private StudentRepository studentRep;
    @Autowired
    private TeacherRepository teacherRep;
    @Autowired
    private SCMRepository SCMRepo;

    @AfterAll
    public void clean() {
        underTest.deleteAll();
        studentRep.deleteAll();
        teacherRep.deleteAll();
        SCMRepo.deleteAll();
    }

    @Test
    void findAll() {

        //given
        ArrayList<Course> courses = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            courses.add(new Course(String.valueOf(i)));
        }

        underTest.saveAll(courses);
        //when
        var result = underTest.findAll();
        //then
        assertIterableEquals(courses,result);
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

        Student student = new Student("login", "name", "surname",
                new Group(), 0, (short) 0, 0.0F, false);

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

        underTest.saveAll(courses);

        Teacher teacher = new Teacher("login", "name", "surname", new Department());
        teacher.setCourses((Set<Course>) courses);

        teacherRep.save(teacher);

        var result = underTest.findAllByTeacherLogin(teacher.getLogin());

        assertIterableEquals(courses,result);
    }

    @Test
    void getStudentsByCourse() {

        List<Student> students = new ArrayList<>();
        students.add(new Student("login1", "name1", "surname1",
                new Group(), 0, (short) 0, 0.0F, false));
        students.add(new Student("login2", "name2", "surname2",
                new Group(), 0, (short) 0, 0.0F, false));

        studentRep.saveAll(students);

        Course course = new Course("course");

        underTest.save(course);

        List<StudentCourseMarks> studMarks = new ArrayList<>();
        studMarks.add( new StudentCourseMarks(students.get(0), course));
        studMarks.add( new StudentCourseMarks(students.get(1), course));

        SCMRepo.saveAll(studMarks);

        var result = underTest.getStudentsByCourse(course.getId());

        assertIterableEquals(students, result);
    }
}