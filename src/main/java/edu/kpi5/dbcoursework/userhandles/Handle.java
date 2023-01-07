package edu.kpi5.dbcoursework.userhandles;

import edu.kpi5.dbcoursework.entities.userdb.User;

public class Handle {
    private User user;

    Handle(User user){
        this.user = user;
    }

    public User getUser(){
        return user;
    }
}
