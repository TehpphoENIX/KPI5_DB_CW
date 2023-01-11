package edu.kpi5.dbcoursework.dbaccess.coredb;

import edu.kpi5.dbcoursework.entities.coredb.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class StudentRepositoryTest {
    @Autowired
    private StudentRepository underTest;
    @Autowired
    private SCMRepository SCMRepo;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private DepartmentRepository departmentRepository;


    Student student1;
    Student student2;
    Student student3;
    Course course1;
    Course course2;
    Course course3;

    StudentCourseMarks stud1scm1;
    StudentCourseMarks stud1scm2;
    StudentCourseMarks stud1scm3;
    StudentCourseMarks stud2scm1;
    StudentCourseMarks stud2scm2;
    StudentCourseMarks stud2scm3;
    StudentCourseMarks stud3scm1;
    StudentCourseMarks stud3scm2;
    StudentCourseMarks stud3scm3;


    @BeforeEach
    void given() {
        //bad student
        student1 = new Student("login1", "Vasia", "Pupkin", null, 1);
        course1 = new Course("Mathematica");
        course2 = new Course("History");
        course3 = new Course("English");

        stud1scm1 = new StudentCourseMarks();
        stud1scm1.setStudent(student1);
        stud1scm1.setSocialWork(false);
        stud1scm1.setCourse(course1);
        stud1scm1.setTotalPoints(1.0);


        stud1scm2 = new StudentCourseMarks();
        stud1scm2.setStudent(student1);
        stud1scm2.setSocialWork(true);
        stud1scm2.setCourse(course2);
        stud1scm2.setTotalPoints(2.0);

        stud1scm3 = new StudentCourseMarks();
        stud1scm3.setStudent(student1);
        stud1scm3.setSocialWork(false);
        stud1scm3.setCourse(course3);
        stud1scm3.setTotalPoints(0.0);

        underTest.save(student1);

        List<Course> courseList = new ArrayList<>();
        courseList.add(course1);
        courseList.add(course2);
        courseList.add(course3);

        List<StudentCourseMarks> scmList = new ArrayList<>();
        scmList.add(stud1scm1);
        scmList.add(stud1scm2);
        scmList.add(stud1scm3);

        //good student
        student2 = new Student("login2", "Roma", "Pupkin", null, 1);

        stud2scm1 = new StudentCourseMarks();
        stud2scm1.setStudent(student2);
        stud2scm1.setSocialWork(true);
        stud2scm1.setCourse(course1);
        stud2scm1.setTotalPoints(3.0);

        stud2scm2 = new StudentCourseMarks();
        stud2scm2.setStudent(student2);
        stud2scm2.setSocialWork(true);
        stud2scm2.setCourse(course2);
        stud2scm2.setTotalPoints(4.0);

        stud2scm3 = new StudentCourseMarks();
        stud2scm3.setStudent(student2);
        stud2scm3.setSocialWork(false);
        stud2scm3.setCourse(course3);
        stud2scm3.setTotalPoints(5.0);

        underTest.save(student2);

        courseList.add(course1);
        courseList.add(course2);
        courseList.add(course3);

        scmList.add(stud2scm1);
        scmList.add(stud2scm2);
        scmList.add(stud2scm3);

        //excellent student
        student3 = new Student("login3", "Masha", "Pupkin", null, 1);

        stud3scm1 = new StudentCourseMarks();
        stud3scm1.setStudent(student3);
        stud3scm1.setSocialWork(false);
        stud3scm1.setCourse(course1);
        stud3scm1.setTotalPoints(5.0);


        stud3scm2 = new StudentCourseMarks();
        stud3scm2.setStudent(student3);
        stud3scm2.setSocialWork(true);
        stud3scm2.setCourse(course2);
        stud3scm2.setTotalPoints(5.0);

        stud3scm3 = new StudentCourseMarks();
        stud3scm3.setStudent(student3);
        stud3scm3.setSocialWork(false);
        stud3scm3.setCourse(course3);
        stud3scm3.setTotalPoints(5.0);

        underTest.save(student3);

        courseList.add(course1);
        courseList.add(course2);
        courseList.add(course3);
        courseRepository.saveAll(courseList);

        scmList.add(stud3scm1);
        scmList.add(stud3scm2);
        scmList.add(stud3scm3);
        SCMRepo.saveAll(scmList);
    }

    @AfterEach
    public void clean(){
        SCMRepo.deleteAll();
        courseRepository.deleteAll();
        underTest.deleteAll();

    }


    @Test
    void sameEntitiesCount() {
        assertEquals(3, underTest.count());
    }

    @Test
    void addStudentGetStudent() {
        var received = underTest.findById(student1.getId()).get();
        assertNotNull(received);
        assertEquals(student1.getId(),received.getId());
    }

    @Test
    void getKickList() {
        var received = underTest.getKickList();
        assertNotNull(received);
        assertTrue(received.size()==1);
        assertEquals(student1.getId(),received.get(0).getId());
    }
    @Test
    void getIncreasedScholarshipList() {
        var received = underTest.getIncreasedScholarshipList();
        assertNotNull(received);
        assertTrue(received.size()==1);
        assertEquals(student3.getId(),received.get(0).getId());
    }
    @Test
    void getScholarshipList() {
        var received = underTest.getScholarshipList();
        assertNotNull(received);
        assertTrue(received.size()==1);
        assertEquals(student2.getId(),received.get(0).getId());
    }

    @Test
    void getStudentsByCourse() {
        List<Student> students = new ArrayList<>();
        students.add(new Student("name1", "name1", "surname1",
                null,0));
        students.add(new Student("name2", "name2", "surname2",
                null,0));
        underTest.saveAll(students);
        Course course = new Course("course");
        courseRepository.save(course);
        List<StudentCourseMarks> studMarks = new ArrayList<>();
        studMarks.add( new StudentCourseMarks(students.get(0), course));
        studMarks.add( new StudentCourseMarks(students.get(1), course));
        SCMRepo.saveAll(studMarks);

        var result = underTest.findByCourse(course.getId());

        assertIterableEquals(students, result);
    }

    @Test
    void circular(){
        Department d = new Department("","");
        Group g = new Group("",0,(short)0,d);
        Student s = new Student("","","",g,0);
        Course c = new Course("");
        StudentCourseMarks scm = new StudentCourseMarks(s,c);
        Teacher t = new Teacher("","","",d);
        departmentRepository.save(d);
        groupRepository.save(g);
        underTest.save(s);
        teacherRepository.save(t);
        courseRepository.save(c);
        SCMRepo.save(scm);

        var recieved = underTest.findAll();
    }
}
