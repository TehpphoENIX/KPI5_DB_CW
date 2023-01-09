package edu.kpi5.dbcoursework.dbaccess.coredb;

import edu.kpi5.dbcoursework.entities.coredb.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class SCMRepositoryTest {

    @Autowired
    private SCMRepository underTest;

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private SCMRepository scmRepository;

    private ArrayList<Student> studentlist= new ArrayList<>();
    private ArrayList<Course> courselist = new ArrayList<>();

    private StudentCourseMarks scm;

    @BeforeEach
    void given() {
        Department d = new Department("d");
        Group g = new Group("g",1,(short)1,d);
        studentlist.add(new Student("A","A","",g,1,(short)0,0.0f,false));
        studentlist.add(new Student("B","B","",g,1,(short)0,0.0f,false));
        courselist.add(new Course("C"));
        courselist.add(new Course("D"));
        scm = new StudentCourseMarks();

        scm.setStudent(studentlist.get(0));
        scm.setCourse(courselist.get(0));
        studentlist.get(0).getMarks().add(scm);
        courselist.get(0).getMarks().add(scm);

        departmentRepository.save(d);
        groupRepository.save(g);
        studentRepository.saveAll(studentlist);
        courseRepository.saveAll(courselist);
        scmRepository.save(scm);
    }

    @AfterEach
    public void clean(){
        underTest.deleteAll();
        studentRepository.deleteAll();
        courseRepository.deleteAll();
        groupRepository.deleteAll();
        departmentRepository.deleteAll();
    }

    @Test
    void findByCourseId() {
        var recieved = underTest.findByCourseId(courselist.get(0).getId());

        assertIterableEquals(recieved,courselist.get(0).getMarks());
    }

    @Test
    void findByStudentId() {
        var recieved = underTest.findByStudentId(studentlist.get(0).getId());

        assertIterableEquals(recieved,studentlist.get(0).getMarks());
    }

    @Test
    void findByStudentIdAndCourseId() {
        var recieved = underTest.findByStudentIdAndCourseId(
                studentlist.get(0).getId(),
                courselist.get(0).getId()
        );

        assertTrue(recieved.isPresent());
        assertEquals(recieved.get(),scm);
    }
}