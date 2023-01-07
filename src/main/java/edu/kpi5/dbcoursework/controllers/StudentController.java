package edu.kpi5.dbcoursework.controllers;

import edu.kpi5.dbcoursework.dbaccess.DBApi;
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
    /**
     * Session beans
     */
    @Resource(name = "sessionScopedBean")
    HttpSessionBean httpSessionBean;
    @Resource(name = "dbApiBean")
    DBApi dbApi;

    /**
     * View student menu
     * @param model -- storage model
     * @return menu view
     */
    @GetMapping(path="/menu")
    public String menu(Model model){
        model.addAttribute("name",httpSessionBean.getAppHandle().getUser().getLogin());
        return "student-menu";
    }

    /**
     * View all marks of a student
     * @return ??
     */
//    @RequestMapping(path="{name}/summary")
//    public String marksSummary() {
//        return "list";
//    }
    @GetMapping(path="/courses")
    public String GetMyCourses() {
        return "course-list";
    }

    /**
     * View student's marks of a course
     * @param model -- storage model
     * @param course -- course id
     * @return view of student's marks of a course
     */
    @GetMapping(path="courses/{course}/marks")
    public String GetMarksOfCourse(Model model, @PathVariable(value = "course") Long course) {
        StudentHandle handle = (StudentHandle) httpSessionBean.getAppHandle();
        model.addAttribute("marks", handle.getMarksOfCourse(course,0l,dbApi));//todo add correct arguments
        return "marks-list";
    }

    // THIS METHOD IS NOT USED
//    @GetMapping(path="/courses/{course}/RSO")
//    public String GetRSOOfCourse(Model model, @PathVariable(value = "course") Long course) {
//        StudentHandle handle = (StudentHandle) httpSessionBean.getAppHandle();
//        model.addAttribute("RSO", handle.GetRSOOfCourse(course));
//        return "RSO";
//    }

    /**
     * View student scholarship
     * @param model -- storage model
     * @return scholarship view
     */
    @GetMapping(path="/scholarship")
    public String CheckScholarship(Model model) {
        StudentHandle handle = (StudentHandle) httpSessionBean.getAppHandle();
        model.addAttribute("scholarship", handle.CheckScholarship(dbApi));
        return "scholarship";
    }
}