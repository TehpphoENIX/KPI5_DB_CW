package edu.kpi5.dbcoursework.userhandles;

import org.springframework.data.util.Pair;

import java.util.ArrayList;

public class AdminHandle extends TeacherHandle{
    public ArrayList<Student> kickStudents(boolean deleteAcounts);
    public int removeUsers(ArrayList<String> userNames);
    public int applyScholarship(ArrayList<Student>, boolean increased);
    public ArrayList<Student> getScholarshipList(boolean increased);
    public boolean createUser(String userName, AccessLevel level, String password);
    public boolean editUser(String userName, AccessLevel level, String password);
    public ArrayList<Pair<String,ArrayList<Pair<Float,Float>>>> getTeachersContribution();
    public User getUser(String userName);
    public ArrayList<User> getUserList();
    public boolean addGroup(String groupName);
    public boolean editGroup(String groupName);
    //to continue
}
