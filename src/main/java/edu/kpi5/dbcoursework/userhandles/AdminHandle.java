package edu.kpi5.dbcoursework.userhandles;

import edu.kpi5.dbcoursework.dbaccess.DBApi;
import edu.kpi5.dbcoursework.entities.*;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdminHandle extends TeacherHandle{
    public AdminHandle(User user) {
        super(user);
    }

    public ArrayList<Student> kickStudents(boolean deleteAcounts)  {
        //як саме повинна працювати дана функція ?
        return null;
    }
    public int removeUsers(ArrayList<String> userNames, DBApi object) {
        //чому повертається значення інт ?
        object.removeUsers(userNames);
        return 0;
    }
    public int applyScholarship(ArrayList<Student> listOfStudents, boolean increased, DBApi object) {
        //чому повертається інт ?
        object.applyScholarship(listOfStudents, increased);
        return 0;
    }
    public ArrayList<Student> getScholarshipList(boolean increased, DBApi object) {
        return new ArrayList<>(object.getScholarshipList(increased));
    }
    public boolean createUser(String userName, AccessLevel level, String password, DBApi object) {
        if(object.createUser(userName,level,password) == true){
            return true;
        }
        return false;
    }
    public boolean editUser(String userName, AccessLevel level, String password, DBApi object) {
        if(object.editUser(userName,level,password) == true){
            return true;
        }
        return false;
    }
    public ArrayList<Pair<String, ArrayList<Pair<Float, Float>>>> getTeachersContribution() {
        // для чого використовується ця функція ?
        return null;
    }
    public User getUser(String userName, DBApi object) {
        return object.getUser(userName);
    }
    public ArrayList<User> getUserList(DBApi object) {
        return new ArrayList<>(object.getUserList());
    }
    public boolean addGroup(String groupName, DBApi object) {
        try{
            object.addGroup(groupName);
            return true;
        }catch (Exception e){

        }
        return false;
    }
    public boolean editGroup(String groupName, DBApi object) {
        try{
            object.editGroup(groupName);
            return true;
        }catch (Exception e){

        }
        return false;
    }

    public boolean addStudentsToGroup(String groupName, ArrayList<Student> students, DBApi object){
        var studentNames = new ArrayList<String>();
        for(var a : students)
            studentNames.add(a.getLogin());
        if(object.addStudentsToGroup(groupName, studentNames)) {
            return true;
        }
        return false;

    }

    public boolean removeGroup(String groupName, DBApi object){
        //в DBApi в методі removeGroup йде видалення за айді, а не за назвою
        object.removeGroup(groupName);
    }

    public Group getGroup(String groupName, DBApi object){
        //в DBApi getGroup повинно повертати не список, а лише одну групу
        return object.getGroup(groupName);
    }

    public ArrayList<Group> getGroupList(DBApi object){
        return new ArrayList<>(object.getGroupList());
    }

    public List<Student> getAllStudents(DBApi object){
        return object.getAllStudents();
    }

    public List<Teacher> getAllTeachers(DBApi object){
        return object.getAllTeachers();
    }
}
