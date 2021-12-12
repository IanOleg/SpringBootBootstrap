package com.crud.controller;

import com.crud.model.User;
import com.crud.service.RoleService;
import com.crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@Controller
public class UsersController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping(value = "/")
    public String loginPage() {

        return "redirect:/login";
    }

    @GetMapping(value = "/admin")
    public String printCarsList(Model model, Principal principal) {

        model.addAttribute("newUser",  new User());
        model.addAttribute("allRoles", roleService.getAllRoles());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("user",  userService.getUserByName(principal.getName()));
        model.addAttribute("usersWithRoleUser", userService.getUsersByRole(roleService.getRole("ROLE_USER")));

        return "home";
    }

    @PostMapping(value = "/admin/removeUser")
    public String removeUser(@ModelAttribute("user") User user) {

        userService.removeUser(user.getId());
        return "redirect:/admin";
    }

    @PostMapping (value = "/admin/mergeUser")
    public String mergeUser(@ModelAttribute("user") User user) {

        userService.mergeUser(user);
        return "redirect:/admin";
    }

    @PostMapping(value = "/admin/saveUser")
    public String saveUser(@ModelAttribute("newUser") User user) {

        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping(value = "/user")
    public String getUser(Model model, Principal principal) {

        model.addAttribute("usersWithRoleUser", userService.getAllUsers(principal.getName()));
        model.addAttribute("user",  userService.getUserByName(principal.getName()));

        return "home";
    }
}
