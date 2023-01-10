package edu.kpi5.dbcoursework.userhandles;

import edu.kpi5.dbcoursework.dbaccess.DBApi;
import edu.kpi5.dbcoursework.entities.coredb.*;
import edu.kpi5.dbcoursework.entities.userdb.AccessLevelEnum;
import edu.kpi5.dbcoursework.entities.userdb.User;
import edu.kpi5.dbcoursework.entities.workDB.Contribution;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class AdminHandle extends TeacherHandle{
    public AdminHandle(User user) {
        super(user);
    }

    public List<Student> kickStudents(boolean deleteAcounts, DBApi object)  {
        List<Student> kickList = object.getKickList();

        if(deleteAcounts){
            List<String> usersToDelete = new ArrayList<>();
            for(var item : kickList) {
                usersToDelete.add(item.getLogin());
            }
            object.removeUsers(usersToDelete);
        }

        return kickList;
    }
    public void removeUsers(List<String> userNames, DBApi object) {
        object.removeUsers(userNames);
    }
    public void applyScholarship(List<Student> listOfStudents, boolean increased, DBApi object) {
        object.applyScholarship(listOfStudents, increased);
    }
    public List<Student> getScholarshipList(boolean increased, DBApi object) {
        return object.getScholarshipList(increased);
    }
    public void createUser(String userName, AccessLevelEnum level, String password, DBApi object) {
        object.createUser(userName,level,password);
    }
//    public boolean editUser(String userName, AccessLevelEnum level, String password, DBApi object) {
//        if(object.editUser(userName,level,password) == true){
//            return true;
//        }
//        return false;
//    }
    public Contribution getTeachersContribution(Long teacherId, DBApi object) {
        return object.getTeachersContribution(teacherId);
    }
    public User getUser(String userName, DBApi object) {
        return object.getUser(userName);
    }
    public List<User> getUserList(DBApi object) {
        return object.getUserList();
    }
    public void addGroup(String groupName, Integer groupYear, Short groupSpec, Department department, DBApi object) {
            object.addGroup(groupName, groupYear, groupSpec, department);
    }
    public void editGroup(Group group, DBApi object) {
        object.editGroup(group);
    }

    public void addStudentsToGroup(Long groupId, ArrayList<Student> students, DBApi object){
        object.addStudentsToGroup(groupId,students);
    }

    public void removeGroup(Long groupId, DBApi object){
        object.removeGroup(groupId);
    }

    public Group getGroup(Long groupId, DBApi object){
        return object.getGroup(groupId);
    }

    public List<Group> getGroupList(DBApi object){
        return object.getGroupList();
    }

    public List<Student> getAllStudents(DBApi object){
        return object.getAllStudents();
    }

    public List<Teacher> getAllTeachers(DBApi object){
        return object.getAllTeachers();
    }

    public Student getStudent(Long studentId, DBApi object){
        return object.getStudent(studentId);
    }
    public Student getTeacher(Long teacherId, DBApi object){
        return object.getStudent(teacherId);
    }

    @Override
    protected void increment(DBApi dbApi){}
}
