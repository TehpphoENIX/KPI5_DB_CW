package edu.kpi5.dbcoursework.userhandles;

import edu.kpi5.dbcoursework.entities.User;
import edu.kpi5.dbcoursework.entities.AccessLevel;

public class Handle {
    private User user;

    Handle(User user){
        this.user = user;
    }

    public User getUser(){
        return user;
    }

    //можливо зайві два наступні?
    public String getLogin(){
        return user.getLogin();
    }

    public AccessLevel getAccessLevel(){
        return user.getAccessLevel();
    }

    public void exit(){
        //не певен, що правильна реалізація, тому позначу для виправлення
        this.user = new User();
    }
}
