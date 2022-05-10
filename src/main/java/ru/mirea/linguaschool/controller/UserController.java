package ru.mirea.linguaschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.linguaschool.model.Student;
import ru.mirea.linguaschool.model.Teacher;
import ru.mirea.linguaschool.model.User;
import ru.mirea.linguaschool.service.StudentService;
import ru.mirea.linguaschool.service.TeacherService;
import ru.mirea.linguaschool.service.UserService;

import java.util.Optional;

@RestController
public class UserController {
    private final TeacherService teacherService;
    private final UserService userService;
    private final StudentService studentService;

    @Autowired
    public UserController(TeacherService teacherService,UserService userService, StudentService studentService) {
        this.teacherService = teacherService;
        this.userService = userService;
        this.studentService = studentService;
    }

    @PutMapping("enroll/{id}")
    public ResponseEntity<Teacher> enroll(@PathVariable long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        Optional<Student> stud = studentService.findStudentById(user.getId());
        Optional<Teacher> teacher = teacherService.findTeacherById(id);
        if (teacher.isEmpty() || stud.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        stud.get().setTeacher(teacher.get());
        studentService.saveStudent(stud.get());
        return ResponseEntity.ok(teacher.get());
    }
}
