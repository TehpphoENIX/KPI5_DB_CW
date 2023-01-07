package edu.kpi5.dbcoursework.userhandles;


import edu.kpi5.dbcoursework.entities.userdb.User;

public class HandleFactory {
    public StudentHandle createStudentHandle(User user){
        return new StudentHandle(user);
    }
    public TeacherHandle createTeacherHandle(User user){
        return new TeacherHandle(user);
    }
    public AdminHandle createAdminHandle(User user){
        return new AdminHandle(user);
    }
}
