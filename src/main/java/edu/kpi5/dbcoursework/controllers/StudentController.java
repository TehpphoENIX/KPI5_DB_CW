package edu.kpi5.dbcoursework.controllers;

import edu.kpi5.dbcoursework.utility.HttpSessionBean;
import edu.kpi5.dbcoursework.userhandles.StudentHandle;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path="student")
public class StudentController {
    @Resource(name = "sessionScopedBean")
    HttpSessionBean httpSessionBean;
    @GetMapping(path="/menu")
    public String menu(Model model){
        model.addAttribute("name",httpSessionBean.getAppHandle().getUser().getLogin());
        return "student-menu";
    }
//    @RequestMapping(path="{name}/summary")
//    public String marksSummary() {
//        return "list";
//    }
    @GetMapping(path="/courses")
    public String GetMyCourses() {
        return "course-list";
    }
    @GetMapping(path="courses/{course}/marks")
    public String GetMarksOfCourse(Model model, @PathVariable(value = "course") Long course) {
        StudentHandle handle = (StudentHandle) httpSessionBean.getAppHandle();
        model.addAttribute("marks", handle.GetMarksOfCourse(course));
        return "marks-list";
    }
//    @GetMapping(path="/courses/{course}/RSO")
//    public String GetRSOOfCourse(Model model, @PathVariable(value = "course") Long course) {
//        StudentHandle handle = (StudentHandle) httpSessionBean.getAppHandle();
//        model.addAttribute("RSO", handle.GetRSOOfCourse(course));
//        return "RSO";
//    }
    @GetMapping(path="/scholarship")
    public String CheckScholarship(Model model) {
        StudentHandle handle = (StudentHandle) httpSessionBean.getAppHandle();
        model.addAttribute("scholarship", handle.CheckScholarship());
        return "scholarship";
    }
}