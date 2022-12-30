package edu.kpi5.dbcoursework.utility;

import edu.kpi5.dbcoursework.dbaccess.DBApi;
import edu.kpi5.dbcoursework.entities.AccessLevel;
import edu.kpi5.dbcoursework.entities.User;
import edu.kpi5.dbcoursework.userhandles.Handle;
import edu.kpi5.dbcoursework.userhandles.HandleFactory;

public class Security {
    public static Handle authorize(DBApi api, String login, String password){
        User user = api.loginToUser(login, password);
        if(user != null){
            return switch(user.getAccessLevel()){
                case AccessLevel.student -> new HandleFactory().createStudentHandle(user);
                case AccessLevel.admin -> new HandleFactory().createAdminHandle(user);
                case AccessLevel.teacher -> new HandleFactory().createTeacherHandle(user);
                case AccessLevel.none -> null;
            };
        }else{
            return null;
        }
    }
}
