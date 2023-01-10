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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class AdminController {
    enum Status{
        welcome("welcome!");

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
    @GetMapping("/menu")
    public String menu(Model model){
        model.addAttribute("name",httpSessionBean.getAppHandle().getUser().getLogin());
        return "admin-menu";
    }

    @GetMapping("users")
    public String userList(Model model){
        AdminHandle handle = (AdminHandle) httpSessionBean.getAppHandle();
        model.addAttribute("users",handle.getUserList(dbApi));
        return "user-list";
    }

    @Getter
    @Setter
    class AddUserForm{
        private String userName;
        private String password;
    }

    @GetMapping("users/add")
    public String sendAddUserForm(Model model){
        AddUserForm form = new AddUserForm();
        model.addAttribute("form",form);
        return "add-user";
    }
    @PostMapping("users/add")
    public String addUser(@ModelAttribute AddUserForm form, Model model){
        AdminHandle handle = (AdminHandle) httpSessionBean.getAppHandle();
        handle.createUser(form.getUserName(), AccessLevelEnum.admin,form.getPassword(),dbApi);
        return "redirect:users";
    }

}
