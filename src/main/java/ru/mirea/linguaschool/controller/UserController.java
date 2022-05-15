package ru.mirea.linguaschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
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

    @PutMapping("enroll/{id}")
    public ResponseEntity<Teacher> enroll(@PathVariable long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        Optional<Teacher> teacher = teacherService.findTeacherById(id);
        if (teacher.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        user.setTeacher(teacher.get());
        userService.updateUser(user);
        return ResponseEntity.ok(teacher.get());
    }
}
