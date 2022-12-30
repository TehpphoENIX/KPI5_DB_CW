package edu.kpi5.dbcoursework.userhandles;

import edu.kpi5.dbcoursework.entities.AccessLevel;
import edu.kpi5.dbcoursework.entities.Student;
import edu.kpi5.dbcoursework.entities.User;
import org.springframework.data.util.Pair;

import java.util.ArrayList;

public class AdminHandle extends TeacherHandle{
    public AdminHandle(User user) {
        super(user);
    }

    public ArrayList<Student> kickStudents(boolean deleteAcounts) {
        return null;
    }
    public int removeUsers(ArrayList<String> userNames) {
        return 0;
    }
    public int applyScholarship(ArrayList<Student> listOfStudents, boolean increased) {
        return 0;
    }
    public ArrayList<Student> getScholarshipList(boolean increased) {
        return null;
    }
    public boolean createUser(String userName, AccessLevel level, String password) {
        return false;
    }
    public boolean editUser(String userName, AccessLevel level, String password) {
        return false;
    }
    public ArrayList<Pair<String, ArrayList<Pair<Float, Float>>>> getTeachersContribution() {
        return null;
    }
    public User getUser(String userName) {
        return null;
    }
    public ArrayList<User> getUserList() {
        return null;
    }
    public boolean addGroup(String groupName) {
        return false;
    }
    public boolean editGroup(String groupName) {
        return false;
    }
    //to continue
}
