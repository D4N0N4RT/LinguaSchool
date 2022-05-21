package ru.mirea.linguaschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.mirea.linguaschool.model.Teacher;
import ru.mirea.linguaschool.model.User;
import ru.mirea.linguaschool.service.TeacherService;
import ru.mirea.linguaschool.service.UserService;

import javax.validation.Valid;

@Controller
public class MainController {
    private UserService userService;
    private TeacherService teacherService;

    @Autowired
    public MainController(UserService userService, TeacherService teacherService) {
        this.userService = userService;
        this.teacherService = teacherService;
    }

    @RequestMapping("/")
    public String main(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) userService.loadUserByUsername(auth.getName());
        model.addAttribute("user", user);
        return "index";
    }

    @PostMapping("/admin/teacher")
    public String addUser(@Valid Teacher teacher) {
        teacherService.saveTeacher(teacher);
        return "redirect:/login";
    }
}
