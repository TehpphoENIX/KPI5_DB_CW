package edu.kpi5.dbcoursework.controllers;

import edu.kpi5.dbcoursework.dbaccess.DBApi;
import edu.kpi5.dbcoursework.entities.coredb.*;
import edu.kpi5.dbcoursework.entities.userdb.AccessLevelEnum;
import edu.kpi5.dbcoursework.presets.Preset;
import edu.kpi5.dbcoursework.userhandles.AdminHandle;
import edu.kpi5.dbcoursework.userhandles.TeacherHandle;
import edu.kpi5.dbcoursework.utility.HttpSessionBean;
import edu.kpi5.dbcoursework.utility.UserForm;
import jakarta.annotation.Resource;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("admin")
public class AdminController {
    enum Status{
        welcome("welcome!"),
        adminCreated("admin user was created!")
        ;

        public String label;
        Status(String label) {
            this.label = label;
        }
    }
    /**
     * Session beans
     */
    @Resource(name = "sessionScopedBean")
    HttpSessionBean httpSessionBean;
    @Autowired
    DBApi dbApi;

    /**
     * Administrator's menu function
     * @param model -- model for showing info
     * @return menu view
     */
    @GetMapping("menu")
    public String menu(Model model, @RequestParam(name = "c",required = false)Status status){
        if(status != null){
            model.addAttribute("status",status.label);
        }
        return "menu/admin";
    }

    @GetMapping("users")
    public String userList(Model model){
        AdminHandle handle = (AdminHandle) httpSessionBean.getAppHandle();
        model.addAttribute("users",handle.getUserList(dbApi));
        return "list/user";
    }
    @Getter
    @Setter
    static class AddUserForm{
        protected String userName;
        protected String password;
    }
    @GetMapping("users/add")
    public String sendAddUserForm(Model model){
        AddUserForm form = new AddUserForm();
        model.addAttribute("form",form);
        return "add/user";
    }
    @PostMapping("users/add")
    public String addUser(@ModelAttribute AddUserForm form, Model model){
        AdminHandle handle = (AdminHandle) httpSessionBean.getAppHandle();
        handle.createUser(form.getUserName(), AccessLevelEnum.admin,form.getPassword(),dbApi);
        return "redirect:/admin/users?c="+Status.adminCreated;
    }
    @GetMapping("users/students")
    public String listStudents(Model model){
        AdminHandle handle = (AdminHandle) httpSessionBean.getAppHandle();
        model.addAttribute("list", handle.getAllStudents(dbApi));
        return "list/student(admin)";
    }
    @Getter
    @Setter
    static class AddStudentForm extends AddUserForm{
        String name;
        String surname;
        Integer hostel;
        Long groupId;
    }
    @GetMapping("users/students/add")
    public String sendAddStudentForm(Model model){
        AddStudentForm form = new AddStudentForm();
        model.addAttribute("form",form);
        AdminHandle handle = (AdminHandle) httpSessionBean.getAppHandle();
        model.addAttribute("list",handle.getGroupList(dbApi));
        return "add/student";
    }
    @PostMapping("users/students/add")
    public String addStudent(@ModelAttribute AddStudentForm form, Model model){
        AdminHandle handle = (AdminHandle) httpSessionBean.getAppHandle();
        Group group = handle.getGroup(form.getGroupId(),dbApi);
        handle.createStudent(form.getUserName(),form.getPassword(),form.getName(),form.getSurname(),form.getHostel(),group,dbApi);
        return "redirect:/admin/users/students";
    }
    @GetMapping("users/students/{name}")
    public String viewStudent(@PathVariable Long name, Model model){
        AdminHandle handle = (AdminHandle) httpSessionBean.getAppHandle();
        model.addAttribute("student",handle.getStudent(name,dbApi));
        return "show/student";
    }
    @GetMapping("users/teachers")
    public String listTeachers(Model model){
        AdminHandle handle = (AdminHandle) httpSessionBean.getAppHandle();
        model.addAttribute("list", handle.getAllTeachers(dbApi));
        return "list/teacher";
    }
    @Getter
    @Setter
    static class AddTeacherForm extends AddUserForm{
        String name;
        String surname;
        Long departmentId;
    }
    @GetMapping("users/teachers/add")
    public String sendAddTeacherForm(Model model){
        AddTeacherForm addTeacherForm = new AddTeacherForm();
        model.addAttribute("form",addTeacherForm);
        AdminHandle handle = (AdminHandle) httpSessionBean.getAppHandle();
        model.addAttribute("list",handle.getAllDepartments(dbApi));
        return "add/teacher";
    }
    @PostMapping("users/teachers/add")
    public String addTeacher(@ModelAttribute AddTeacherForm form, Model model){
        AdminHandle handle = (AdminHandle) httpSessionBean.getAppHandle();
        handle.createTeacher(form.getUserName(),form.getPassword(),form.getName(),form.getSurname(),form.getDepartmentId(),dbApi);
        return "redirect:/admin/users/teachers";
    }
    @GetMapping("users/teachers/{name}")
    public String viewTeacher(@PathVariable Long name, Model model){
        AdminHandle handle = (AdminHandle) httpSessionBean.getAppHandle();
        model.addAttribute("teacher",handle.getTeacher(name,dbApi));
        return "show/teacher";
    }

    @GetMapping("users/{name}")
    public String viewUser(@PathVariable String name, Model model){
        AdminHandle handle = (AdminHandle) httpSessionBean.getAppHandle();
        model.addAttribute("user",handle.getUser(name,dbApi));
        return "show/user";
    }
    @GetMapping("users/{name}/delete")
    public String removeUser(@PathVariable String name){
        AdminHandle handle = (AdminHandle) httpSessionBean.getAppHandle();
        List<String> listToDelete = new ArrayList<>();
        listToDelete.add(name);
        handle.removeUsers(listToDelete,dbApi);
        return "redirect:/admin/users";
    }

    @GetMapping("groups")
    public String viewGroups(Model model){
        AdminHandle handle = (AdminHandle) httpSessionBean.getAppHandle();
        model.addAttribute("list",handle.getGroupList(dbApi));
        return "list/group";
    }
    @Getter
    @Setter
    static class AddGroupForm{
        private String groupName;
        private Integer groupYear;
        private Short groupSpec;
        private Long departmentId;
    }
    @GetMapping("groups/add")
    public String sendAddGroupForm(Model model){
        AddGroupForm form = new AddGroupForm();
        model.addAttribute("form",form);
        AdminHandle handle = (AdminHandle) httpSessionBean.getAppHandle();
        model.addAttribute("list", handle.getAllDepartments(dbApi));
        return "add/group";
    }
    @PostMapping("groups/add")
    public String addGroup(@ModelAttribute AddGroupForm form, Model model){
        AdminHandle handle = (AdminHandle) httpSessionBean.getAppHandle();
        handle.addGroup(form.getGroupName(),form.getGroupYear(),form.groupSpec,form.getDepartmentId(),dbApi);//todo
        return "redirect:/admin/groups";
    }
    @GetMapping("groups/{id}")
    public String viewGroup(@PathVariable Long id, Model model){
        AdminHandle handle = (AdminHandle) httpSessionBean.getAppHandle();
        model.addAttribute("group",handle.getGroup(id,dbApi));
        return "show/group";
    }
    @GetMapping("groups/{id}/remove")
    public String removeGroup(@PathVariable Long id, Model model){
        AdminHandle handle = (AdminHandle) httpSessionBean.getAppHandle();
        handle.removeGroup(id,dbApi);
        return "redirect:/admin/groups";
    }
    @GetMapping("kick")
    public String getKickList(Model model){
        AdminHandle handle = (AdminHandle) httpSessionBean.getAppHandle();
        model.addAttribute("list",handle.kickStudents(false,dbApi));
        return "list/kick";
    }
    @GetMapping("scholarship")
    public String getScholarshipList(Model model){
        AdminHandle handle = (AdminHandle) httpSessionBean.getAppHandle();
        model.addAttribute("list1",handle.getScholarshipList(false,dbApi));
        model.addAttribute("list2",handle.getScholarshipList(true,dbApi));
        return "list/scholar";
    }
    @PostMapping("scholarship")
    public String applyScholarship(){
        AdminHandle handle = (AdminHandle) httpSessionBean.getAppHandle();
        handle.applyScholarship(handle.getScholarshipList(false,dbApi),false,dbApi);
        handle.applyScholarship(handle.getScholarshipList(true,dbApi),true,dbApi);
        return "redirect:/admin";
    }
    @GetMapping("/courses")
    public String getCourseList(Model model) {
        AdminHandle handle = (AdminHandle) httpSessionBean.getAppHandle();
        model.addAttribute("courses", handle.getCourseList(dbApi));
        return "courses";
    }


    @GetMapping("/courses/{course}")
    public String getCourse(@PathVariable(value = "course") Long courseId, Model model) {
        AdminHandle handle = (AdminHandle) httpSessionBean.getAppHandle();
        model.addAttribute("course", handle.getCourse(courseId, dbApi));
        return "course";
    }

    @GetMapping("/courses/{course}/delete")
    public String getCourseDeleteConfirm(@PathVariable(value = "course") Long courseId, Model model) {
        model.addAttribute("id", courseId);
        return "teacher-course-delete";
    }

    @PostMapping("/courses/{course}/delete")
    public String removeCourse(@PathVariable(value = "course") Long courseId) {
        AdminHandle handle = (AdminHandle) httpSessionBean.getAppHandle();
        handle.removeCourse(courseId, dbApi); //TODO somehow it not works
        return "redirect:/admin/courses";
    }
    @Getter
    @Setter
    static class EditCourseForm{
        private long courseId;
        private String courseName;
        private Set<StudentCourseMarks> marks;
        private Set<Teacher> teachers;
    }
    @GetMapping("/courses/{course}/edit")
    public String getCourseEdit(@PathVariable(value = "course") Long courseId, Model model, EditCourseForm form) {
        AdminHandle handle = (AdminHandle) httpSessionBean.getAppHandle();
        model.addAttribute("course", handle.getCourse(courseId, dbApi));
        return "teacher-course-edit";
    }

    @PostMapping("/courses/{course}/edit")
    public String editCourse(@RequestParam EditCourseForm form) {
        AdminHandle handle = (AdminHandle) httpSessionBean.getAppHandle();

        Course newCourse = new Course(form.courseName);
        newCourse.setId(form.courseId);
        newCourse.setTeachers(form.teachers);
        newCourse.setMarks(form.marks);
        handle.editCourse(newCourse, dbApi);
        return "redirect:/admin/courses";
    }
//
//    @GetMapping("/teacher/courses/add")
//    public String sendAddCourseForm(Model model){
//        model.addAttribute("form",null);//todo make handle
//        return "add/course";
//    }
//    @PostMapping("/teacher/courses/add")
//    public String addCourse(Model model){}

}
