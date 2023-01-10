package edu.kpi5.dbcoursework.controllers;

import edu.kpi5.dbcoursework.dbaccess.DBApi;
import edu.kpi5.dbcoursework.entities.userdb.AccessLevel;
import edu.kpi5.dbcoursework.entities.userdb.AccessLevelEnum;
import edu.kpi5.dbcoursework.entities.userdb.User;
import edu.kpi5.dbcoursework.userhandles.HandleFactory;
import edu.kpi5.dbcoursework.utility.HttpSessionBean;
import edu.kpi5.dbcoursework.utility.Security;
import edu.kpi5.dbcoursework.utility.UserForm;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * This is default web controller
 * It handles login and redirection to user menu
 * SPRING CONTROLLER USE ONLY!
 */
@Controller
public class UserController {

    enum ErrorCode{
        authorizationFailed("authorization failed");

        public String label;

        ErrorCode(String label){
            this.label = label;
        }
    }
    /**
     * Bean links
     */
    @Resource(name = "sessionScopedBean")
    HttpSessionBean httpSessionBean;
    @Autowired
    DBApi dbApi;

    /**
     * Home mapping. Redirects to log in or user methods.
     * @return view
     */
    @GetMapping(path="/")
    public String deflt(){
        if(httpSessionBean.getAppHandle() == null){
            return "redirect:/login";
        }else{
            return "redirect:/user/"+httpSessionBean.getAppHandle().getUser().getLogin();
        }
    }

    /**
     * Login GET mapping. Shows login screen. Supplies form to fill.
     * @param model -- new model
     * @return view
     */
    @GetMapping(path="/login")
    public String loginForm(Model model, @RequestParam(name = "error", required = false) ErrorCode code){
        model.addAttribute("userform", new UserForm());
        if(code != null){
            model.addAttribute("error",code.label);
        }
        return "login-screen";
    }

    /**
     * Login POST mapping. Processes received form. Authorizes user and redirects to home page
     * @param userForm -- received user form
     * @param model -- new model
     * @return redirect
     */
    @PostMapping(path="/login")
    public String loginRequest(@ModelAttribute UserForm userForm, Model model){
        httpSessionBean.setAppHandle(Security.authorize(dbApi,userForm.getLogin(),userForm.getPassword()));
        if(httpSessionBean.getAppHandle() == null)
        {
            model.addAttribute("error","authorization failed");
            return "redirect:/login?error="+ErrorCode.authorizationFailed;
        }else{
            return "redirect:/user/"+httpSessionBean.getAppHandle().getUser().getLogin();
        }

    }

    /**
     * Redirects to an appropriate menu
     * @param name -- user login
     * @return -- redirect to menu
     */
    @GetMapping(path="/user/{name}")
    public String forwardMenuRequest(@PathVariable String name){

        User user = httpSessionBean.getAppHandle().getUser();

        if(user.getAccessLevels().contains(new AccessLevel(AccessLevelEnum.admin.label))){
            return "redirect:/admin/menu?c=" + AdminController.Status.welcome;
        }else if(user.getAccessLevels().contains(new AccessLevel(AccessLevelEnum.teacher.label))){
            return "redirect:/teacher/menu";
        }else if(user.getAccessLevels().contains(new AccessLevel(AccessLevelEnum.student.label))){
            return "redirect:/student/menu";
        }
        throw new IllegalStateException("user had accessLevel=none");
    }//todo

    /**
     * Deletes user handle and redirects to home page
     * @return redirect to home
     */
    @GetMapping(path="/exit")
    public String exit() {
        httpSessionBean.setAppHandle(null);
        return "redirect:/";
    }
}
