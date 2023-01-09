package edu.kpi5.dbcoursework.dbaccess.coredb;

import edu.kpi5.dbcoursework.entities.coredb.Course;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CourseRepositoryTest {

    @Autowired
    private CourseRepository underTest;

    @AfterEach
    public void clean(){
        underTest.deleteAll();
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
    void findByName() {
    }

    @Test
    void findAllByStudentLogin() {
    }

    @Test
    void findAllByTeacherLogin() {
    }

    @Test
    void getStudentsByCourse() {
    }
}