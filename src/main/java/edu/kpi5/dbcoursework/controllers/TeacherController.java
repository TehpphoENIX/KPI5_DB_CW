package edu.kpi5.dbcoursework.controllers;

import edu.kpi5.dbcoursework.beans.HttpSessionBean;
import edu.kpi5.dbcoursework.dbaccess.repositories.CourseRepository;
import edu.kpi5.dbcoursework.dbaccess.repositories.GroupRepository;
import edu.kpi5.dbcoursework.dbaccess.repositories.StudentRepository;
import edu.kpi5.dbcoursework.entities.Course;
import edu.kpi5.dbcoursework.entities.Student;
import edu.kpi5.dbcoursework.userhandles.StudentHandle;
import edu.kpi5.dbcoursework.userhandles.TeacherHandle;
import edu.kpi5.dbcoursework.utility.MarksList;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping("teacher")
public class TeacherController {
    @Resource(name = "sessionScopedBean")
    HttpSessionBean httpSessionBean;

    @GetMapping("/menu")
    public String menu(Model model){
        model.addAttribute("name",httpSessionBean.getAppHandle().getUser().getLogin());
        return "teacher-menu";
    }
    @PostMapping("/courses/{course}/attest/set")
    public String setAttestation(@PathVariable(value = "course") String courseName, @RequestParam MarksList marksList) {

        TeacherHandle handle = (TeacherHandle) httpSessionBean.getAppHandle();
        handle.setAttestation(courseName, marksList);
        return "redirect:/courses/{course}";
    }
    @PostMapping("/courses/{course}/mark/set")
    public String setMark(@PathVariable(value = "course") String courseName, @RequestParam Student student,@RequestParam int mark) {
        TeacherHandle handle = (TeacherHandle) httpSessionBean.getAppHandle();
        handle.setMark(courseName, student, mark);
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
    @GetMapping("/courses/{course}")
    public String getCourse(@PathVariable(value = "course") String courseName, Model model) {
        TeacherHandle handle = (TeacherHandle) httpSessionBean.getAppHandle();
        model.addAttribute("course", handle.getCourse(courseName));
        return "course";
    }
    @GetMapping("/courses/{course}/students")
    public String getCourseStudents(@PathVariable(value = "course") String courseName, Model model) {
        TeacherHandle handle = (TeacherHandle) httpSessionBean.getAppHandle();
        model.addAttribute("course-students", handle.getCourseStudents(courseName));
        return "course-students";
    }
    @GetMapping("/courses")
    public String getCourseList(Model model) {
        TeacherHandle handle = (TeacherHandle) httpSessionBean.getAppHandle();
        model.addAttribute("courses", handle.getCourseList());
        return "courses";
    }
}
