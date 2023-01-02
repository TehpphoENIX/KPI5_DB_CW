package edu.kpi5.dbcoursework.beans;

import edu.kpi5.dbcoursework.entities.User;
import edu.kpi5.dbcoursework.userhandles.Handle;
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

    private Handle appHandle;

    public Handle getAppHandle() {
        return appHandle;
    }

    public void setAppHandle(Handle appHandle) {
        this.appHandle = appHandle;
    }
}
