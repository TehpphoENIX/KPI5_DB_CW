package edu.kpi5.dbcoursework.controllers;

import edu.kpi5.dbcoursework.dbaccess.DBApi;
import edu.kpi5.dbcoursework.entities.userdb.AccessLevelEnum;
import edu.kpi5.dbcoursework.userhandles.AdminHandle;
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
    public static class AddUserForm{
        private String userName;
        private String password;
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
        return "redirect:admin/users?с="+Status.adminCreated;
    }
    @GetMapping("users/students")
    public String listStudents(Model model){
        AdminHandle handle = (AdminHandle) httpSessionBean.getAppHandle();
        model.addAttribute("list", handle.getAllStudents(dbApi));
        return "list/student(admin)";
    }
    @GetMapping("users/teachers")
    public String listTeachers(Model model){
        AdminHandle handle = (AdminHandle) httpSessionBean.getAppHandle();
        model.addAttribute("list", handle.getAllTeachers(dbApi));
        return "list/teacher";
    }
    @GetMapping("users/{name}")
    public String viewUser(@PathVariable String name, Model model){
        AdminHandle handle = (AdminHandle) httpSessionBean.getAppHandle();
        model.addAttribute("user",handle.getUser(name,dbApi));
        return "show/user";
    }
//    @GetMapping("users/{name}/delete")
//    public String removeUser(@PathVariable String name){
//        return "";
//    }
    @GetMapping("users/students/{name}")
    public String viewStudent(@PathVariable Long name, Model model){
        AdminHandle handle = (AdminHandle) httpSessionBean.getAppHandle();
        model.addAttribute("student",handle.getStudent(name,dbApi));
        return "show/student";
    }
    @GetMapping("users/teachers/{name}")
    public String viewTeacher(@PathVariable Long name, Model model){
        AdminHandle handle = (AdminHandle) httpSessionBean.getAppHandle();
        model.addAttribute("teacher",handle.getTeacher(name,dbApi));
        return "show/teacher";
    }
    @GetMapping("groups")
    public String viewGroups(Model model){
        AdminHandle handle = (AdminHandle) httpSessionBean.getAppHandle();
        model.addAttribute("list",handle.getGroupList(dbApi));
        return "list/group";
    }
    @Getter
    @Setter
    public static class AddGroupForm{
        private String groupName;
        private Integer groupYear;
        private Short groupSpec;
    }
    @GetMapping("groups/add")
    public String sendAddGroupForm(Model model){
        AddGroupForm form = new AddGroupForm();
        model.addAttribute("form",form);
        return "add/group";
    }
    @PostMapping("groups/add")
    public String addGroup(@ModelAttribute AddGroupForm form, Model model){
        AdminHandle handle = (AdminHandle) httpSessionBean.getAppHandle();
        handle.addGroup(form.getGroupName(),form.getGroupYear(),form.groupSpec,null,dbApi);//todo
        return "redirect:admin/groups";
    }
}
