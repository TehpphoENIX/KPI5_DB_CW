package edu.kpi5.dbcoursework.preset;

import edu.kpi5.dbcoursework.dbaccess.repositories.CourseRepository;
import edu.kpi5.dbcoursework.dbaccess.repositories.GroupRepository;
import edu.kpi5.dbcoursework.dbaccess.repositories.StudentRepository;
import edu.kpi5.dbcoursework.entities.Course;
import edu.kpi5.dbcoursework.entities.Group;
import edu.kpi5.dbcoursework.entities.Student;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner1 implements CommandLineRunner {
    private GroupRepository groupRepository;
    private StudentRepository studentRepository;
    private CourseRepository courseRepository;

    public Runner1(GroupRepository groupRepository, StudentRepository studentRepository, CourseRepository courseRepository) {
        this.groupRepository = groupRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public void run(String... args) throws Exception{
        System.out.println("runner 1");
        Group da01 = new Group("DA-01",2020,(short)122);
        Student name = new Student("Name","Surname",da01,1,(short)1,1.0f,false);
        Course spring = new Course("Spring");

        name.getCourses().add(spring);
        spring.getStudents().add(name);
        System.out.println("number of courses "+courseRepository.count());

    }
}
