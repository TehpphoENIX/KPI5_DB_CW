package edu.kpi5.dbcoursework.controllers;

import edu.kpi5.dbcoursework.beans.HttpSessionBean;
import edu.kpi5.dbcoursework.entities.User;
import edu.kpi5.dbcoursework.utility.UserForm;
import io.netty.util.internal.StringUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

@Controller
public class UserController {
    @Resource(name = "sessionScopedBean")
    HttpSessionBean httpSessionBean;
    @GetMapping(path="/")
    public String deflt(){
        if(httpSessionBean.getUser() == null || StringUtils.isEmpty(httpSessionBean.getUser().getLogin())){
            return "redirect:/login";
        }else{
            return "redirect:/user/"+httpSessionBean.getUser().getLogin();
        }
    }
    @GetMapping(path="/login")
    public String loginForm(Model model){
        model.addAttribute("userform", new UserForm());
        return "login-screen";
    }
    @PostMapping(path="/login")
    public String loginRequest(@ModelAttribute UserForm userForm, Model model){
        httpSessionBean.setUser(new User());
        httpSessionBean.getUser().setLogin(userForm.getLogin());
        return "redirect:/user/"+userForm.getLogin();
    }
    @GetMapping(path="/user/{name}")
    public String forwardMenuRequest(@PathVariable String name){
        return "redirect:/student/menu";
    }
    @GetMapping(path="/exit")
    public String exit() {
        httpSessionBean.setUser(null);
        return "redirect:/";
    }
}
