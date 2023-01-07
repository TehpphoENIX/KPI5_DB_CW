package edu.kpi5.dbcoursework.utility;

import edu.kpi5.dbcoursework.dbaccess.DBApi;
import edu.kpi5.dbcoursework.userhandles.Handle;
import edu.kpi5.dbcoursework.userhandles.HandleFactory;

public class Security {
    public static Handle authorize(DBApi api, String login, String password){
        User user = api.loginToUser(login, password);
        if(user != null){
            return switch(user.getAccessLevel()){
                case student -> new HandleFactory().createStudentHandle(user);
                case admin -> new HandleFactory().createAdminHandle(user);
                case teacher -> new HandleFactory().createTeacherHandle(user);
                case none -> null;
            };
        }else{
            return null;
        }
    }
}
