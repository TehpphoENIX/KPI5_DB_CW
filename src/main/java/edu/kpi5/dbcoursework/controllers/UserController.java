package edu.kpi5.dbcoursework.controllers;

import edu.kpi5.dbcoursework.beans.HttpSessionBean;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final HttpSessionBean httpSessionBean;

    public UserController(HttpSessionBean httpSessionBean) {
        this.httpSessionBean = httpSessionBean;
    }


}
