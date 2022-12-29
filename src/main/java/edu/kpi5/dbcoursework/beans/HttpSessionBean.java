package edu.kpi5.dbcoursework.beans;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@SessionScope
@Component
public class HttpSessionBean {
    private String name;
}
