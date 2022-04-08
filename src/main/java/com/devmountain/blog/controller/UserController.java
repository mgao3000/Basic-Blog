package com.devmountain.blog.controller;

import com.devmountain.blog.model.User;
import com.devmountain.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String getAllUsers(Model model, @PageableDefault(sort= {"username"}, value = 5)Pageable pageable) {
        Page<User> users = this.userService.findAll(pageable);

        model.addAttribute("users", users);
        return "users/index";
    }
}
