package edu.kpi5.dbcoursework.controllers;

import edu.kpi5.dbcoursework.dbaccess.DBApi;
import edu.kpi5.dbcoursework.entities.marksdb.MarksList;
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
        handle.setMarks(courseName, marksList, dbApi);
        return "redirect:/courses/{course}";
    }
    @PostMapping("/courses/{course}/social_work/set")
    public String setSocialWork(@PathVariable(value = "course") String courseName,@RequestParam MarksList marksList) {
        TeacherHandle handle = (TeacherHandle) httpSessionBean.getAppHandle();
        handle.setSocialWork(courseName, marksList, dbApi);
        return "redirect:/courses/{course}";
    }
    @PostMapping("/courses/{course}/exam/set")
    public String setExam(@PathVariable(value = "course") String courseName,@RequestParam MarksList marksList) {
        TeacherHandle handle = (TeacherHandle) httpSessionBean.getAppHandle();
        handle.setExam(courseName, marksList, dbApi);
        return "redirect:/courses/{course}";
    }
    @PostMapping("/courses/add")
    public String addCourse(@RequestParam String courseName, @RequestParam ArrayList<String> groups) {
        TeacherHandle handle = (TeacherHandle) httpSessionBean.getAppHandle();
        handle.addCourse(courseName, groups, dbApi);
        return "redirect:/courses/"+courseName;
    }
    @PostMapping("/courses/edit")
    public String editCourse(@RequestParam Course newCourse) {
        TeacherHandle handle = (TeacherHandle) httpSessionBean.getAppHandle();
        handle.editCourse(newCourse, dbApi);
        return "redirect:/courses/"+newCourse;
    }
    @PostMapping("/courses/delete")
    public String removeCourse(@RequestParam Long courseId) {
        TeacherHandle handle = (TeacherHandle) httpSessionBean.getAppHandle();
        handle.removeCourse(courseId, dbApi);
        return "redirect:/courses";
    }

    /**
     * View information of a course
     * @param courseId -- Course ID
     * @param model -- model to output info about course
     * @return course view
     */
    @GetMapping("/courses/{course}")
    public String getCourse(@PathVariable(value = "course") Long courseId, Model model) {
        TeacherHandle handle = (TeacherHandle) httpSessionBean.getAppHandle();
        model.addAttribute("course", handle.getCourse(courseId, dbApi));
        return "course";
    }

    /**
     * View students of a course
     * @param courseId -- Course ID
     * @param model -- model to store students list
     * @return -- course students view
     */
    @GetMapping("/courses/{course}/students")
    public String getCourseStudents(@PathVariable(value = "course") Long courseId, Model model) {
        TeacherHandle handle = (TeacherHandle) httpSessionBean.getAppHandle();
        model.addAttribute("course-students", handle.getCourseStudents(handle.getCourse(courseId, dbApi), dbApi));
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
        model.addAttribute("courses", handle.getCourseList(dbApi));
        return "courses";
    }

    @GetMapping("/courses/{course}/edit")
    public String getCourseEdit(@PathVariable(value = "course") Long courseId, Model model) {
        TeacherHandle handle = (TeacherHandle) httpSessionBean.getAppHandle();
        model.addAttribute("course-students", handle.getCourse(courseId, dbApi));
        return "teacher-course-edit";
    }

    @GetMapping("/courses/{course}/delete")
    public String getCourseDeleteConfirm(@PathVariable(value = "course") Long courseId, Model model) {
        model.addAttribute("course-id", courseId);
        return "teacher-course-delete";
    }

    @GetMapping("/courses/{course}/add-marks")
    public String getCourseAddMArks(@PathVariable(value = "course") Long courseId, Model model) {
        model.addAttribute("course-id", courseId);
        return "teacher-add-marks";
    }

    @GetMapping("/courses/{course}/add-social-work")
    public String getCourseAddSocialWork(@PathVariable(value = "course") Long courseId, Model model) {
        model.addAttribute("course-id", courseId);
        return "teacher-add-social-work";
    }

    @GetMapping("/courses/{course}/add-exam")
    public String getCourseAddExam(@PathVariable(value = "course") Long courseId, Model model) {
        model.addAttribute("course-id", courseId);
        return "teacher-add-exam";
    }
}
