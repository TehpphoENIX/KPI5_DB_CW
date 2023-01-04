package edu.kpi5.dbcoursework.preset;

import edu.kpi5.dbcoursework.dbaccess.coredb.CourseRepository;
import edu.kpi5.dbcoursework.dbaccess.coredb.GroupRepository;
import edu.kpi5.dbcoursework.dbaccess.coredb.StudentRepository;
import edu.kpi5.dbcoursework.dbaccess.marksdb.StudentCourseMarksRepository;
import edu.kpi5.dbcoursework.entities.coredb.Course;
import edu.kpi5.dbcoursework.entities.coredb.Group;
import edu.kpi5.dbcoursework.entities.coredb.Student;
import edu.kpi5.dbcoursework.entities.marksdb.StudentCourseMarks;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner1 implements CommandLineRunner {
    private GroupRepository groupRepository;
    private StudentRepository studentRepository;
    private CourseRepository courseRepository;

    private StudentCourseMarksRepository studentCourseMarksRepository;

    public Runner1(GroupRepository groupRepository, StudentRepository studentRepository, CourseRepository courseRepository, StudentCourseMarksRepository studentCourseMarksRepository) {
        this.groupRepository = groupRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.studentCourseMarksRepository = studentCourseMarksRepository;
    }

    @Override
    public void run(String... args) throws Exception{
        System.out.println("runner 1");
        Group da01 = new Group("DA-01",2020,(short)122);
        Student name = new Student("Name","Surname",da01,1,(short)1,1.0f,false);
        Course spring = new Course("Spring");
        StudentCourseMarks marks = new StudentCourseMarks("test");

        name.getCourses().add(spring);
        spring.getStudents().add(name);

        groupRepository.save(da01);
        studentRepository.save(name);
        courseRepository.save(spring);
        studentCourseMarksRepository.save(marks);

        System.out.println("number of courses "+courseRepository.count());

    }
}
