package edu.kpi5.dbcoursework.controllers;

import edu.kpi5.dbcoursework.beans.HttpSessionBean;
import edu.kpi5.dbcoursework.entities.Course;
import edu.kpi5.dbcoursework.utility.MarksList;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;

@Controller
@RequestMapping(path="student")
public class StudentController {
    @Resource(name = "sessionScopedBean")
    HttpSessionBean httpSessionBean;
    @RequestMapping(path="menu")
    public String menu(Model model){
        model.addAttribute("name",httpSessionBean.getUser().getLogin());
        return "student-menu";
    }
//    @RequestMapping(path="{name}/summary")
//    public String marksSummary() {
//        return "list";
//    }
    @RequestMapping(path="courses")
    public String GetMyCourses() {
        return "course-list";
    }
    @RequestMapping(path="courses/{course}/marks")
    public String GetMarksOfCourse() {
        return "marks-list";
    }
    @RequestMapping(path="courses/{course}/RSO")
    public MarksList GetRSOOfCourse(String courseName) {
        return null;
    }
    @RequestMapping(path="scholarship")
    public int CheckScholarship() {
        return 0;
    }
}