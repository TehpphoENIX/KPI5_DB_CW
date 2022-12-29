package edu.kpi5.dbcoursework.beans;

import edu.kpi5.dbcoursework.entities.User;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

//@SessionScope
@Component
public class HttpSessionBean {
    @Bean
    @SessionScope
    public HttpSessionBean sessionScopedBean() {
        return new HttpSessionBean();
    }
    private User user;
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
