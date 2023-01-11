package edu.kpi5.dbcoursework.controllers;

import edu.kpi5.dbcoursework.dbaccess.DBApi;
import edu.kpi5.dbcoursework.entities.marksdb.MarksList;
import edu.kpi5.dbcoursework.userhandles.AdminHandle;
import edu.kpi5.dbcoursework.userhandles.StudentHandle;
import edu.kpi5.dbcoursework.utility.HttpSessionBean;
import edu.kpi5.dbcoursework.entities.coredb.*;
import edu.kpi5.dbcoursework.userhandles.TeacherHandle;
import jakarta.annotation.Resource;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Set;

@Controller
@RequestMapping("teacher")
public class TeacherController {
    /**
     * Session beans
     */
    @Resource(name = "sessionScopedBean")
    HttpSessionBean httpSessionBean;
    @Autowired
    DBApi dbApi;

    /**
     * Teacher's menu function
     * @param model -- model for showing info
     * @return menu view
     */
    @GetMapping("/menu")
    public String menu(Model model){
        return "menu/teacher";
    }

    //THIS FUNCTION IS NOT USED
//    @PostMapping("/courses/{course}/attest/set")
//    public String setAttestation(@PathVariable(value = "course") String courseName, @RequestParam MarksList marksList) {
//
//        TeacherHandle handle = (TeacherHandle) httpSessionBean.getAppHandle();
//        handle.setAttestation(courseName, marksList, dbApi);
//        return "redirect:/courses/{course}";
//    }
    @Getter
    @Setter
    class SetMarkForm{
        private String markName;
        private Long studentId;
        private Integer value;
    }
//    @GetMapping("/courses/{course}/mark/set")
//    public String sendSetMarkForm(@PathVariable(value = "course") Long courseId, Model model){
//        SetMarkForm form = new SetMarkForm();
//        model.addAttribute("form",form);
//        TeacherHandle handle = (TeacherHandle) httpSessionBean.getAppHandle();
//        model.addAttribute("listOfStudents",handle.getCourseStudents(courseId,dbApi));
//        model.addAttribute("list",handle.getCourseStudents(courseId,dbApi));
//        return "teacher-add-marks";
//    }
//    @PostMapping("/courses/{course}/mark/set")
//    public String setMark(@PathVariable(value = "course") Long courseId, @ModelAttribute SetMarkForm form) {
//        TeacherHandle handle = (TeacherHandle) httpSessionBean.getAppHandle();
//        handle.setMark(courseId, form.getMarkName(), form.getStudentId(), form.getValue(), dbApi);
//        return "redirect:/courses/{course}";
//    }
//    @PostMapping("/courses/{course}/marks/set")
//    public String setMarks(@PathVariable(value = "course") Long courseId,  @RequestParam MarksList marksList) {
//        TeacherHandle handle = (TeacherHandle) httpSessionBean.getAppHandle();
////        handle.setMarks(courseId, marksList, dbApi);
//
//        return "redirect:/courses/{course}";
//    }
//    @PostMapping("/courses/{course}/social_work/set")
//    public String setSocialWork(@PathVariable(value = "course") Long courseId, @RequestParam Long studentID, @RequestParam Boolean isSocialWork) {
//        TeacherHandle handle = (TeacherHandle) httpSessionBean.getAppHandle();
//
//        handle.setSocialWork(courseId, studentID, dbApi);
//
//        return "redirect:/courses/{course}";
//    }
//    @PostMapping("/courses/{course}/exam/set")
//    public String setExam(@PathVariable(value = "course")  Long courseId,@RequestParam MarksList marksList) {
//        TeacherHandle handle = (TeacherHandle) httpSessionBean.getAppHandle();
//
////        handle.setExam(courseId, marksList, dbApi);
//
//        return "redirect:/courses/{course}";
//    }
//    @PostMapping("/courses/{course}/edit")
//    public String editCourse(@PathVariable(value = "course") @RequestParam long CourseId, @RequestParam String CourseName) {
//        TeacherHandle handle = (TeacherHandle) httpSessionBean.getAppHandle();
//
//        Course newCourse = new Course(CourseName);
//        newCourse.setId(CourseId);
//
//        //TODO якось зробити ввід цього з форми html
//        Set<StudentCourseMarks> marks = handle.getCourse(CourseId, dbApi).getMarks();
//        newCourse.setMarks(marks);
//        Set<Teacher> teachers = handle.getCourse(CourseId, dbApi).getTeachers();
//        newCourse.setTeachers(teachers);
//
//        handle.editCourse(newCourse, dbApi);
//        return "redirect:/courses/"+newCourse;
//
//    }
    //@PostMapping("/courses/delete")
    //public String removeCourse(@RequestParam Long courseId) {
    //    TeacherHandle handle = (TeacherHandle) httpSessionBean.getAppHandle();
    //    handle.removeCourse(courseId, dbApi);
    //    return "redirect:/courses";
    //}

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
        model.addAttribute("marks",handle.getMarksOfCourse(courseId, dbApi));
        return "show/course";
    }

    @Getter
    @Setter
    class AddCourseForm{
        private String courseName;
        private ArrayList<Long> groups;
    }
    @GetMapping("/courses/add")
    public String addCourseForm(Model model){
        AddCourseForm form = new AddCourseForm();
        TeacherHandle handle = (TeacherHandle) httpSessionBean.getAppHandle();
        model.addAttribute("form",form);
        model.addAttribute("list",handle.getGroupList(dbApi));

        return "add/course";
    }
    @PostMapping("/courses/add")
    public String addCourse(@ModelAttribute AddCourseForm form) {
        TeacherHandle handle = (TeacherHandle) httpSessionBean.getAppHandle();
        handle.addCourse(form.courseName,form.groups, dbApi);
        return "redirect:../courses";
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
        model.addAttribute("students", handle.getCourseStudents(courseId, dbApi));
        model.addAttribute("id",courseId);
        return "list/student";
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
        return "list/course";
    }

//    @GetMapping("/courses/{course}/edit")
//    public String getCourseEdit(@PathVariable(value = "course") Long courseId, Model model) {
//        TeacherHandle handle = (TeacherHandle) httpSessionBean.getAppHandle();
//        model.addAttribute("course-students", handle.getCourse(courseId, dbApi));
//        return "teacher-course-edit";
//    }

    @GetMapping("/courses/{course}/delete")
    //public String getCourseDeleteConfirm(@PathVariable(value = "course") Long courseId, Model model) {
    //    model.addAttribute("course-id", courseId);
    //    return "teacher-course-delete";
    //}
    public String removeCourse(@PathVariable("course") Long courseId) {
        TeacherHandle handle = (TeacherHandle) httpSessionBean.getAppHandle();
        handle.removeCourse(courseId, dbApi);
        return "redirect:/teacher/courses";
    }

    @GetMapping("/courses/{course}/add-marks")
    public String getCourseAddMArks(@PathVariable(value = "course") Long courseId, Model model) {
        SetMarkForm form = new SetMarkForm();
        model.addAttribute("form",form);
        TeacherHandle handle = (TeacherHandle) httpSessionBean.getAppHandle();
        model.addAttribute("list", handle.getCourseStudents(courseId, dbApi));
        model.addAttribute("courseId",courseId);
        return "add/mark";
    }
    @PostMapping("/courses/{course}/add-marks")
    public String addMarks(@ModelAttribute SetMarkForm form, @PathVariable("course") Long courseId){
        TeacherHandle handle = (TeacherHandle) httpSessionBean.getAppHandle();
        handle.setMark(courseId, form.getMarkName(), form.getStudentId(), form.getValue(), dbApi);
        return "redirect:/teacher/courses/"+courseId;
    }

    @GetMapping("/courses/{course}/add-social-work")
    public String getCourseAddSocialWork(@PathVariable(value = "course") Long courseId, Model model) {
        SetMarkForm form = new SetMarkForm();
        model.addAttribute("form",form);
        TeacherHandle handle = (TeacherHandle) httpSessionBean.getAppHandle();
        model.addAttribute("list",handle.getCourseStudents(courseId,dbApi));
        model.addAttribute("course-id", courseId);
        return "add/social-work";
    }
    @PostMapping("/courses/{course}/add-social-work")
    public String addSocialWork(@PathVariable(value = "course") Long courseId, @ModelAttribute SetMarkForm form){
        TeacherHandle handle = (TeacherHandle) httpSessionBean.getAppHandle();
        handle.setSocialWork(courseId,form.getStudentId(),dbApi);
        return "redirect:/teacher/courses/"+courseId;
    }

//    @GetMapping("/courses/{course}/add-exam")
//    public String getCourseAddExam(@PathVariable(value = "course") Long courseId, Model model) {
//        model.addAttribute("course-id", courseId);
//        return "add/exam";
//    }

    @GetMapping(path="/about")
    public String About(Model model) {
        TeacherHandle handle = (TeacherHandle) httpSessionBean.getAppHandle();
        model.addAttribute("teacher", handle.getTeacher());
        return "about/teacher";
    }
}
