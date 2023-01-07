package edu.kpi5.dbcoursework.controllers;

import edu.kpi5.dbcoursework.dbaccess.DBApi;
import edu.kpi5.dbcoursework.utility.HttpSessionBean;
import edu.kpi5.dbcoursework.entities.coredb.*;
import edu.kpi5.dbcoursework.userhandles.TeacherHandle;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("teacher")
public class TeacherController {
    /**
     * Session beans
     */
    @Resource(name = "sessionScopedBean")
    HttpSessionBean httpSessionBean;
    @Resource(name = "dbApiBean")
    DBApi dbApi;

    /**
     * Teacher's menu function
     * @param model -- model for showing info
     * @return menu view
     */
    @GetMapping("/menu")
    public String menu(Model model){
        model.addAttribute("name",httpSessionBean.getAppHandle().getUser().getLogin());
        return "teacher-menu";
    }

    //THIS FUNCTION IS NOT USED
//    @PostMapping("/courses/{course}/attest/set")
//    public String setAttestation(@PathVariable(value = "course") String courseName, @RequestParam MarksList marksList) {
//
//        TeacherHandle handle = (TeacherHandle) httpSessionBean.getAppHandle();
//        handle.setAttestation(courseName, marksList, dbApi);
//        return "redirect:/courses/{course}";
//    }

    @PostMapping("/courses/{course}/mark/set")
    public String setMark(@PathVariable(value = "course") String courseName, @RequestParam Student student, @RequestParam int mark) {
        TeacherHandle handle = (TeacherHandle) httpSessionBean.getAppHandle();
        handle.setMark(courseName, student, mark, dbApi);
        return "redirect:/courses/{course}";
    }
    @PostMapping("/courses/{course}/marks/set")
    public String setMarks(@PathVariable(value = "course") String courseName,  @RequestParam MarksList marksList) {
        TeacherHandle handle = (TeacherHandle) httpSessionBean.getAppHandle();
        handle.setMarks(courseName, marksList);
        return "redirect:/courses/{course}";
    }
    @PostMapping("/courses/{course}/social_work/set")
    public String setSocialWork(@PathVariable(value = "course") String courseName,@RequestParam MarksList marksList) {
        TeacherHandle handle = (TeacherHandle) httpSessionBean.getAppHandle();
        handle.setSocialWork(courseName, marksList);
        return "redirect:/courses/{course}";
    }
    @PostMapping("/courses/{course}/exam/set")
    public String setExam(@PathVariable(value = "course") String courseName,@RequestParam MarksList marksList) {
        TeacherHandle handle = (TeacherHandle) httpSessionBean.getAppHandle();
        handle.setExam(courseName, marksList);
        return "redirect:/courses/{course}";
    }
    @PostMapping("/courses/add")
    public String addCourse(@RequestParam String courseName, @RequestParam ArrayList<String> groups) {
        TeacherHandle handle = (TeacherHandle) httpSessionBean.getAppHandle();
        handle.addCourse(courseName, groups);
        return "redirect:/courses/"+courseName;
    }
    @PostMapping("/courses/edit")
    public String editCourse(@RequestParam String courseName, @RequestParam String newCourseName) {
        TeacherHandle handle = (TeacherHandle) httpSessionBean.getAppHandle();
        handle.editCourse(courseName, newCourseName);
        return "redirect:/courses/"+newCourseName;
    }
    @PostMapping("/courses/delete")
    public String removeCourse(@RequestParam String courseName) {
        TeacherHandle handle = (TeacherHandle) httpSessionBean.getAppHandle();
        handle.removeCourse(courseName);
        return "redirect:/courses";
    }

    /**
     * View information of a course
     * @param courseName -- todo
     * @param model -- model to output info about course
     * @return course view
     */
    @GetMapping("/courses/{course}")
    public String getCourse(@PathVariable(value = "course") String courseName, Model model) {
        TeacherHandle handle = (TeacherHandle) httpSessionBean.getAppHandle();
        model.addAttribute("course", handle.getCourse(courseName, dbApi));
        return "course";
    }

    /**
     * View students of a course
     * @param courseName -- todo
     * @param model -- model to store students list
     * @return -- course students view
     */
    @GetMapping("/courses/{course}/students")
    public String getCourseStudents(@PathVariable(value = "course") String courseName, Model model) {
        TeacherHandle handle = (TeacherHandle) httpSessionBean.getAppHandle();
        model.addAttribute("course-students", handle.getCourseStudents(courseName, dbApi));
        return "course-students";
    }

    /**
     * View list of courses linked to teacher
     * @param model -- storage model
     * @return courses list view
     */
    @GetMapping("/courses")
    public String getCourseList(Model model) {
        TeacherHandle handle = (TeacherHandle) httpSessionBean.getAppHandle();
        model.addAttribute("courses", handle.getCourseList());
        return "courses";
    }
}
