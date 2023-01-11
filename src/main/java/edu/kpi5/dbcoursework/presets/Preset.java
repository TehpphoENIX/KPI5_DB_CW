package edu.kpi5.dbcoursework.presets;

import edu.kpi5.dbcoursework.dbaccess.coredb.DepartmentRepository;
import edu.kpi5.dbcoursework.dbaccess.userdb.AccessLevelRepository;
import edu.kpi5.dbcoursework.dbaccess.userdb.UserRepository;
import edu.kpi5.dbcoursework.entities.coredb.Department;
import edu.kpi5.dbcoursework.entities.userdb.AccessLevel;
import edu.kpi5.dbcoursework.entities.userdb.AccessLevelEnum;
import edu.kpi5.dbcoursework.entities.userdb.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class Preset implements CommandLineRunner {

    @Autowired
    private AccessLevelRepository accessLevelRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    public static final Department[] departments = {
            new Department("CAD", "IASA"),
            new Department("MMSA","IASA")
    };

    public static final User root = new User("root","root");
    public static final AccessLevel[] accessLevels = {
            new AccessLevel(AccessLevelEnum.admin.label),
            new AccessLevel(AccessLevelEnum.student.label),
            new AccessLevel(AccessLevelEnum.teacher.label),
    };
    static {
        root.getAccessLevels().add(accessLevels[0]);//ensure to de admin access level
    }

    @Override
    public void run(String... args){
        //departmentRepository.deleteAll();
        for (var item : departments) {
            if (departmentRepository.findByNameAndFaculty(item.getName(), item.getFaculty()).isEmpty()){
                departmentRepository.save(item);
            }
        }
        accessLevelRepository.saveAll(Arrays.stream(accessLevels).toList());
        userRepository.save(root);
        System.out.println("\n\n=====================\nApp finished loading");
    }
}
