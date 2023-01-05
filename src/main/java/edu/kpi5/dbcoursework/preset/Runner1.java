package edu.kpi5.dbcoursework.preset;

import edu.kpi5.dbcoursework.dbaccess.coredb.CourseRepository;
import edu.kpi5.dbcoursework.dbaccess.coredb.GroupRepository;
import edu.kpi5.dbcoursework.dbaccess.coredb.StudentRepository;
import edu.kpi5.dbcoursework.dbaccess.marksdb.MarksListRepository;
import edu.kpi5.dbcoursework.entities.coredb.Course;
import edu.kpi5.dbcoursework.entities.coredb.Department;
import edu.kpi5.dbcoursework.entities.coredb.Group;
import edu.kpi5.dbcoursework.entities.coredb.Student;
import edu.kpi5.dbcoursework.entities.marksdb.MarksList;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class Runner1 implements CommandLineRunner {
    private GroupRepository groupRepository;
    private StudentRepository studentRepository;
    private CourseRepository courseRepository;

    private MarksListRepository marksListRepository;

    public Runner1(GroupRepository groupRepository, StudentRepository studentRepository, CourseRepository courseRepository, MarksListRepository marksListRepository) {
        this.groupRepository = groupRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.marksListRepository = marksListRepository;
    }

    @Override
    public void run(String... args) throws Exception{
        System.out.println("runner 1");
        Department d = new Department("d");
        Group da01 = new Group("DA-01",2020,(short)122,);
        Student name = new Student("","A","Surname",da01,1,(short)1,1.0f,false);
        Course spring = new Course("Spring");
        MarksList marks = new MarksList("test",new ArrayList<>());
        marks.getField().add(new MarksList.Mark("mark2",2));


        groupRepository.save(da01);
        studentRepository.save(name);
        courseRepository.save(spring);
        marksListRepository.save(marks);

        System.out.println("number of courses "+courseRepository.count());

    }
}
