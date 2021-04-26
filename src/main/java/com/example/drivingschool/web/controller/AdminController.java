package com.example.drivingschool.web.controller;

import com.example.drivingschool.model.User;
import com.example.drivingschool.service.RoleService;
import com.example.drivingschool.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String getAdminDefaultPage() {
        return "redirect:/admin/authorization";
    }

    @GetMapping("/authorization")
    public String getAuthorizationPage(Model model) {
        model.addAttribute("users", userService.getAll());
        model.addAttribute("bodyContent", "admin_authorization");

        return "master-template";
    }

    @GetMapping("/statistics")
    public String getStatisticsPage(Model model) {
        model.addAttribute("bodyContent", "admin_statistics");

        return "master-template";
    }

    @PostMapping("/authorization")
    public String addRoleForUsername(
            @RequestParam String role,
            @RequestParam String username
    ) {
//        System.out.println("ROLE IS " + role);
//        System.out.println("USERNAME IS " + username);
        User user = userService.addRoleForUserWithUsername(role, username);

        // smelly code
        if (role.equals("INSTRUCTOR"))
            roleService.createInstructorTableEntryForUser(user);
        if (role.equals("CANDIDATE"))
            roleService.createCandidateTableEntryForUser(user);

        return "redirect:/admin";
    }
}
