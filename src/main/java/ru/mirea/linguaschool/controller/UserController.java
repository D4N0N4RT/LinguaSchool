package ru.mirea.linguaschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.mirea.linguaschool.model.Teacher;
import ru.mirea.linguaschool.model.User;
import ru.mirea.linguaschool.service.TeacherService;
import ru.mirea.linguaschool.service.UserService;

import java.util.Optional;

@Controller
public class UserController {
    private final TeacherService teacherService;
    private final UserService userService;

    @Autowired
    public UserController(TeacherService teacherService,UserService userService) {
        this.teacherService = teacherService;
        this.userService = userService;
    }

    @PostMapping("enroll/{id}")
    public String enroll(@PathVariable long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        Optional<Teacher> teacher = teacherService.findTeacherById(id);
        if (teacher.isEmpty()) {
            return "index";
        }
        user.setTeacher(teacher.get());
        //userService.updateUser(user);
        return "teachers";
    }

    @PostMapping("/expel")
    public String expel() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        user.setTeacher(null);
        return "teachers";
    }
}
