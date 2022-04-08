package com.devmountain.blog.controller;

import com.devmountain.blog.form.LoginForm;
import com.devmountain.blog.model.User;
import com.devmountain.blog.service.NotificationService;
import com.devmountain.blog.service.UserService;
import com.devmountain.blog.util.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/users/login")
    public String login(LoginForm loginForm) {
        return "users/login";
    }

    @PostMapping("/users/login")
//    @RequestMapping(value = "/users/login", method = RequestMethod.POST)
    public String loginPage(@Valid LoginForm loginForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            displayBindingResultErrors(bindingResult.getAllErrors());
            notificationService.addErrorMessage("Please make sure you have filled the form correctly.");
            return "users/login";
        }

        if(!userService.authenticate(loginForm.getUsername(), loginForm.getPassword())) {
            notificationService.addErrorMessage("Invalid login, please try again.");
            return "users/login";
        }

        notificationService.addInfoMessage("Welcome! Login successful");
        return "redirect:/";
    }

    private void displayBindingResultErrors(List<ObjectError> allErrors) {
        int index = 1;
        for(ObjectError objError : allErrors) {
            System.out.println("=====Error No."+index+"="+objError.toString());
            index++;
        }
    }

    /**
     * Display user's registration form
     */
    @GetMapping("/users/register")
    public ModelAndView displayRegistration() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject(user);
        modelAndView.setViewName("users/register");
        return modelAndView;
    }

    /**
     * Do the registration
     */
    @PostMapping("/users/register")
    public ModelAndView doRegistration(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("users/register");

        User retrievedUser = this.userService.findByUserName(user.getUsername());
        if(retrievedUser != null) {
            bindingResult.rejectValue("userName", "error.user", "User already exists");
        }
        if(!bindingResult.hasErrors()) {
            this.userService.create(user);
            modelAndView.addObject("successMessage", "User (" + user.getUsername() +
                    ") has been created");
            modelAndView.addObject("user", new User());
        }
        return modelAndView;
    }

    @PostMapping("/users/logout")
    public String destroySession(HttpServletRequest request) {
        SessionUtils.logout(request);
        return "redirect:/";
    }
}
