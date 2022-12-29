package edu.kpi5.dbcoursework.controllers;

import edu.kpi5.dbcoursework.beans.HttpSessionBean;
import edu.kpi5.dbcoursework.entities.Course;
import edu.kpi5.dbcoursework.entities.Student;
import edu.kpi5.dbcoursework.utility.MarksList;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("teacher")
public class TeacherController {
    @Resource(name = "sessionScopedBean")
    HttpSessionBean httpSessionBean;
    @RequestMapping("menu")
    public String menu(Model model){
        model.addAttribute("name",httpSessionBean.getUser().getLogin());
        return "teacher-menu";
    }
    @RequestMapping("courses/{course}/attest/set")
    public int setAttestation(String courseName, MarksList marksList) {
        return 0;
    }

    public int setMark(String courseName, Student Student, int mark) {
        return 0;
    }

    public int setMarks(String courseName, MarksList marksList) {
        return 0;
    }

    public int setSocialWork(String courseName, MarksList marksList) {
        return 0;
    }

    public int setExam(String courseName, MarksList marksList) {
        return 0;
    }

    public boolean addCourse(String courseName, ArrayList<String> groups) {
        return false;
    }

    public boolean editCourse(String courseName, String newCourseName) {
        return false;
    }

    public boolean removeCourse(String courseName) {
        return false;
    }

    public String getCourse(String courseName) {
        return null;
    }

    public ArrayList<Student> getCourseStudents(String courseName) {
        return null;
    }
    @RequestMapping("courses")
    public String getCourseList() {
        return null;
    }
}
