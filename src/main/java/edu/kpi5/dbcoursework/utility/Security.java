package edu.kpi5.dbcoursework.utility;

import edu.kpi5.dbcoursework.dbaccess.DBApi;
import edu.kpi5.dbcoursework.entities.userdb.AccessLevel;
import edu.kpi5.dbcoursework.entities.userdb.AccessLevelEnum;
import edu.kpi5.dbcoursework.entities.userdb.User;
import edu.kpi5.dbcoursework.userhandles.Handle;
import edu.kpi5.dbcoursework.userhandles.HandleFactory;

public class Security {
    public static Handle authorize(DBApi api, String login, String password){
        User user = api.loginToUser(login, password);
        if(user != null){
            if(user.getAccessLevel().contains(new AccessLevel(AccessLevelEnum.admin.label))){
                return new HandleFactory().createAdminHandle(user);
            }else if(user.getAccessLevel().contains(new AccessLevel(AccessLevelEnum.teacher.label))){
                return new HandleFactory().createTeacherHandle(user);
            }else if(user.getAccessLevel().contains(new AccessLevel(AccessLevelEnum.student.label))){
                return new HandleFactory().createStudentHandle(user);
            }
        }
        return null;
    }
}
