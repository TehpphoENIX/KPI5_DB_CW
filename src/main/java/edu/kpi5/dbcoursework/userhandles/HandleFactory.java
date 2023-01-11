package edu.kpi5.dbcoursework.userhandles;


import edu.kpi5.dbcoursework.dbaccess.DBApi;
import edu.kpi5.dbcoursework.entities.userdb.User;

public class HandleFactory {
    public StudentHandle createStudentHandle(User user, DBApi dbApi){
        return new StudentHandle(user, dbApi);
    }
    public TeacherHandle createTeacherHandle(User user, DBApi dbApi){
        return new TeacherHandle(user, dbApi);
    }
    public AdminHandle createAdminHandle(User user){
        return new AdminHandle(user);
    }
}
